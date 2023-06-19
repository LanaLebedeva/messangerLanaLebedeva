package com.github.lanalebedeva.mydiscord.tirecycle.viewholder

import android.view.View
import com.github.lanalebedeva.mydiscord.databinding.UserItemBinding
import com.github.lanalebedeva.mydiscord.profile.data.model.User
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class UserViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<User>(view, clicks) {

    private val binding = UserItemBinding.bind(view)

    override fun bind(item: User) {
        binding.userItemTvName.text = item.name
        binding.userItemTvEmail.text = item.email
    }
}
