package com.github.lanalebedeva.mydiscord.tirecycle

import android.view.View
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.tirecycle.viewholder.DateDataViewHolder
import com.github.lanalebedeva.mydiscord.tirecycle.viewholder.MessageDataViewHolder
import com.github.lanalebedeva.mydiscord.tirecycle.viewholder.UserViewHolder
import com.github.lanalebedeva.mydiscord.tirecycle.action.CustomClick
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_rx2.actions.TiRecyclerCustomActionObservable
import ru.tinkoff.mobile.tech.ti_recycler_rx2.base.RxHolderFactory
import kotlin.reflect.KClass

class MyDiscordTiRecyclerHolderFactory: RxHolderFactory() {

    private val customClick: CustomClickObservable = CustomClickObservable()
    override val customActions: Map<KClass<*>, TiRecyclerCustomActionObservable<*, *>>
        get() = mapOf(CustomClick::class to customClick)

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<*>? {
        return when (viewType) {
            R.layout.data_item -> DateDataViewHolder(view, clicks)
            R.layout.message_with_reaction_item -> MessageDataViewHolder(view, customClick)
            R.layout.user_item -> UserViewHolder(view, clicks)
            else -> null
        }
    }
}
