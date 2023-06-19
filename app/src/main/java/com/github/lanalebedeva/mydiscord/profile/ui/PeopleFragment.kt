package com.github.lanalebedeva.mydiscord.profile.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.databinding.FragmentPeopleBinding
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.github.lanalebedeva.mydiscord.profile.ui.viewModel.PeopleViewModel
import com.github.lanalebedeva.mydiscord.tirecycle.MyDiscordTiRecyclerHolderFactory
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler_rx2.TiRecyclerRx
import javax.inject.Inject

class PeopleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: PeopleViewModel

    private lateinit var disposable: Disposable

    private val binding: FragmentPeopleBinding by viewBinding(CreateMethod.INFLATE)
    private lateinit var recycler: TiRecyclerRx<ViewTyped>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val holderFactory = MyDiscordTiRecyclerHolderFactory()
        recycler = TiRecyclerRx<ViewTyped>(
            recyclerView = binding.fragmentPeopleRecyclerView,
            holderFactory = holderFactory,
        ) {
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        }
        setupObserver()
        disposable = Observable.mergeArray(
            recycler.clickedItem<User>(R.layout.user_item).map {
                it
            })
                .subscribe {
                    val bundle = bundleOf("user" to it)
                    findNavController(view).navigate(R.id.action_people_dest_to_profile_dest, bundle)
                }
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewStateChat ->
                    when (viewStateChat) {
                        is ViewStateChat.Data -> {
                            binding.apply {
                                fragmentPeopleLoader.visibility = View.GONE
                                fragmentPeopleTvError.visibility = View.GONE
                                fragmentPeopleRecyclerView.visibility = View.VISIBLE
                            }
                            recycler.setItems(viewStateChat.data)
                        }

                        is ViewStateChat.Error -> {
                            binding.apply {
                                fragmentPeopleLoader.visibility = View.GONE
                                fragmentPeopleTvError.visibility = View.VISIBLE
                            }
                        }

                        ViewStateChat.Init -> {}
                        ViewStateChat.Loading -> {
                            binding.apply {

                                fragmentPeopleLoader.visibility = View.VISIBLE
                                fragmentPeopleTvError.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
}
