package com.github.lanalebedeva.mydiscord.channel.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.channel.data.model.StreamLayoutData
import com.github.lanalebedeva.mydiscord.channel.ui.viewModel.ChannelViewModel
import com.github.lanalebedeva.mydiscord.databinding.FragmentCommonStreamBinding
import com.github.lanalebedeva.mydiscord.messages.ui.MessageListFragment
import com.github.lanalebedeva.mydiscord.viewStateChat.ViewStateChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM1 = "subscribe"
class CommonStreamFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: ChannelViewModel

    private val binding: FragmentCommonStreamBinding by viewBinding(CreateMethod.INFLATE)

    private var justSubscribe = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            justSubscribe = it.getBoolean(ARG_PARAM1)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver(view, justSubscribe)

    }

    private fun setupObserver(view: View, justSubscribe: Boolean) {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                when (justSubscribe) {
                    true -> {
                        viewModel.viewStateSubscribe
                            .collect {
                                render(it, view)
                            }
                    }
                    false -> {
                        viewModel.viewState
                            .collect { viewStateChat ->
                            render(viewStateChat, view)
                        }
                    }
                }
            }
        }
    }


private fun render(
    viewStateChat: ViewStateChat<StreamLayoutData>,
    view: View
) {
    when (viewStateChat) {
        is ViewStateChat.Data -> {
            binding.apply {
                fragmentStreamChannelsTvError.visibility = View.GONE
                fragmentStreamChannelsLoader.visibility = View.GONE
                fragmentStreamExpandableListView.visibility = View.VISIBLE
            }
            initExpandableList(view, viewStateChat.data)
        }

        is ViewStateChat.Error -> {
            binding.apply {
                fragmentStreamChannelsTvError.visibility = View.VISIBLE
                fragmentStreamChannelsLoader.visibility = View.GONE
            }
        }

        ViewStateChat.Init -> {}
        ViewStateChat.Loading -> {
            binding.apply {
                fragmentStreamChannelsTvError.visibility = View.GONE
                fragmentStreamChannelsLoader.visibility = View.VISIBLE
            }
        }
    }
}


private fun initExpandableList(view: View, item: StreamLayoutData) {
    val expandableListView = binding.fragmentStreamExpandableListView
    val expandableListDetail =
        item.expandableListDetail
    val expandableListTitle = ArrayList<String>(expandableListDetail.keys)
    val expandableListAdapter =
        CustomizedAdapter(view.context, expandableListTitle, expandableListDetail)

    expandableListView.setAdapter(expandableListAdapter)
    expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
        val bundle = bundleOf(
            MessageListFragment.TOPIC to expandableListDetail[expandableListTitle[groupPosition]]?.title.toString(),
            MessageListFragment.STREAM to expandableListDetail[expandableListTitle[groupPosition]]?.topics?.get(
                childPosition
            )
        )
        findNavController().navigate(R.id.messageList_dest, bundle)
        false
    }
}

companion object {
    @JvmStatic
    fun newInstance(subscribe: Boolean) =
        CommonStreamFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_PARAM1, subscribe)
            }
        }
}
}
