package com.github.lanalebedeva.mydiscord.messages.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.databinding.FragmentMessageListBinding
import com.github.lanalebedeva.mydiscord.messages.ui.viewModel.MessageListViewModel
import com.github.lanalebedeva.mydiscord.tirecycle.MyDiscordTiRecyclerHolderFactory
import com.github.lanalebedeva.mydiscord.viewModel.MessageViewModelFactory
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler_rx2.TiRecyclerRx
import javax.inject.Inject

class MessageListFragment : Fragment() {

    private val binding: FragmentMessageListBinding by viewBinding(CreateMethod.INFLATE)

    private lateinit var recycler: TiRecyclerRx<ViewTyped>

    private val viewModel: MessageListViewModel by viewModels {
        factory.create(
            arguments?.getString(TOPIC) ?: "",
            arguments?.getString(STREAM) ?: ""
        )
    }
    @Inject
    lateinit var factory: MessageViewModelFactory.Factory

//    @Inject
//    lateinit var  holderFactory: MyDiscordTiRecyclerHolderFactory

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMessageToolbar = binding.listMessageToolBar
        listMessageToolbar.setNavigationIcon(R.drawable.back)
        listMessageToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.channels_dest)
        }

        binding.listMessageTvHeader.text =
            context?.getString(R.string.topic, arguments?.getString(STREAM))

        val fragmentActivity = activity
        fragmentActivity?.findViewById<BottomNavigationView>(R.id.main_activity_bottom_navigation)?.visibility =
            View.GONE

        val holderFactory = MyDiscordTiRecyclerHolderFactory()
        recycler = TiRecyclerRx<ViewTyped>(
            recyclerView = binding.recyclerView,
            holderFactory,
        ) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        setupObserver()

        binding.fab.setOnClickListener {
            hideKeyboard(requireActivity())

            val message = binding.listSendMessage
            viewModel.addMessage(message.text.toString())
            message.text?.clear()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewStateChat ->
                    when (viewStateChat) {
                        is ViewStateChat.Data -> {
                            binding.apply {
                                listMessageLoader.visibility = View.GONE
                                listMessageTvError.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }
                            recycler.setItems(viewStateChat.data)
                        }

                        is ViewStateChat.Error -> {
                            binding.apply {
                                listMessageLoader.visibility = View.GONE
                                listMessageTvError.visibility = View.VISIBLE

                            }

                        }

                        ViewStateChat.Init -> {}
                        ViewStateChat.Loading -> {
                            binding.apply {
                                listMessageLoader.visibility = View.VISIBLE
                                listMessageTvError.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        requireActivity().findViewById<BottomNavigationView>(R.id.main_activity_bottom_navigation)?.visibility =
            View.VISIBLE
        super.onPause()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val STREAM = "stream"
        const val TOPIC = "topic"
    }
}
