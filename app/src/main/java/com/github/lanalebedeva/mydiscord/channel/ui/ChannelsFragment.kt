package com.github.lanalebedeva.mydiscord.channel.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.App
import com.github.lanalebedeva.mydiscord.databinding.FragmentChannelsBinding
import com.github.lanalebedeva.mydiscord.channel.ui.viewModel.ChannelViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChannelsFragment : Fragment() {

    private val binding: FragmentChannelsBinding by viewBinding(CreateMethod.INFLATE)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: ChannelViewModel

    private lateinit var viewStateAdapter: ViewStateAdapter
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager2


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
        initTabViewPager()
        binding.fragmentChannelsEditTextSearch.addTextChangedListener {
            lifecycleScope.launch {
                it?.let { query -> viewModel.onQueryChanged(query.toString()) }
            }
        }
    }

    override fun onStop() {
        mTabLayout.removeOnTabSelectedListener(onTabSelectedListener(mViewPager))
        super.onStop()
    }

    private fun initTabViewPager() {
        mViewPager = binding.fragmentChannelsViewPager
        mTabLayout = binding.fragmentChannelsLayoutTabs
        viewStateAdapter = ViewStateAdapter(childFragmentManager, lifecycle)
        mViewPager.adapter = viewStateAdapter
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager))
    }

    private fun onTabSelectedListener(mViewPager: ViewPager2) =
        object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
}
