package com.github.lanalebedeva.mydiscord.profile.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.databinding.FragmentProfileBinding
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import com.github.lanalebedeva.mydiscord.profile.ui.viewModel.ProfileViewModel
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var profileViewModel: ProfileViewModel

    private val binding: FragmentProfileBinding by viewBinding(CreateMethod.INFLATE)
    private var user:User? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = arguments?.getParcelable( "user")
        if( user != null){
            bind(user!!)
        } else {
            profileViewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (user == null) {
            setupObserver()
        }
    }

    private fun bind(user: User) {
        binding.apply {
            fragmentProfileName.text = user.name

            fragmentProfileStatusIdle.visibility =
                when (user.statusIdle) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            fragmentProfileStatusOnline.visibility =
                when (user.statusOnline) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            fragmentProfileStatusOffline.visibility =
                when (!user.statusOnline) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            fragmentProfileTvError.visibility = View.GONE
            fragmentProfileLoader.visibility = View.GONE
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.viewState.collect { viewStateChat ->
                    when (viewStateChat) {
                        is ViewStateChat.Data -> {
                            binding.apply {
                                fragmentProfileName.text = viewStateChat.data.name

                                fragmentProfileStatusIdle.visibility =
                                    when (viewStateChat.data.statusIdle) {
                                        true -> View.VISIBLE
                                        false -> View.GONE
                                    }
                                fragmentProfileStatusOnline.visibility =
                                    when (viewStateChat.data.statusOnline) {
                                        true -> View.VISIBLE
                                        false -> View.GONE
                                    }
                                fragmentProfileStatusOffline.visibility =
                                    when (!viewStateChat.data.statusOnline) {
                                        true -> View.VISIBLE
                                        false -> View.GONE
                                    }
                                fragmentProfileTvError.visibility = View.GONE
                                fragmentProfileLoader.visibility = View.GONE
                            }
                        }

                        is ViewStateChat.Error -> {
                            binding.apply {
                                fragmentProfileTvError.visibility = View.VISIBLE
                                fragmentProfileLoader.visibility = View.GONE
                            }
                        }

                        ViewStateChat.Init -> {}
                        ViewStateChat.Loading -> {
                            binding.apply {
                                fragmentProfileTvError.visibility = View.GONE
                                fragmentProfileLoader.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}
