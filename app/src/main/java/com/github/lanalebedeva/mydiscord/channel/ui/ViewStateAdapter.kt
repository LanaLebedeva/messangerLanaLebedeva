package com.github.lanalebedeva.mydiscord.channel.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUMBER_OF_DEFAULT_TAB = 2
private const val SUBSCRIBED_STREAM_ID = 0
private const val ALL_STREAM_ID = 1

class ViewStateAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUMBER_OF_DEFAULT_TAB

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SUBSCRIBED_STREAM_ID -> CommonStreamFragment.newInstance(subscribe = true)
            ALL_STREAM_ID -> CommonStreamFragment.newInstance(subscribe = false)
            else -> error("ViewStateAdapter")
        }
    }
}
