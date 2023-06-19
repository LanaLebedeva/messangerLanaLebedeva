package com.github.lanalebedeva.mydiscord.tirecycle.viewholder

import android.view.View
import android.view.ViewGroup
import com.github.lanalebedeva.mydiscord.databinding.MessageWithReactionItemBinding
import com.github.lanalebedeva.mydiscord.messages.data.model.MessageData
import com.github.lanalebedeva.mydiscord.tirecycle.CustomClickObservable
import com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview.MessageWithReaction
import com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview.ReactionView
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder

class MessageDataViewHolder(
    view: View,
    customActionClick: CustomClickObservable,
) : BaseViewHolder<MessageData>(view) {

    private val viewMessageData = view
    private val viewMessage = view as MessageWithReaction
    private val binding = MessageWithReactionItemBinding.bind(view)
    private val flexBoxLayout = viewMessage.getChildAt(3)

    init {
        customActionClick.accept(this, flexBoxLayout as View)
    }

    override fun bind(item: MessageData) {
        (viewMessageData as MessageWithReaction).setIsMe(item.isMeMessage)
        if (!item.isMeMessage) {
            binding.image.visibility = View.VISIBLE
            binding.tvName.visibility = View.VISIBLE
            binding.tvName.text = item.senderFullName
//            binding.image = item.avatarUrl
        } else {
            binding.image.visibility = View.GONE
            binding.tvName.visibility = View.GONE
        }
        binding.tvMessage.text = item.content
        val listViewReaction = mutableListOf<ReactionView>()
        if (listViewReaction.isNotEmpty()) {
            binding.viewFlexbox.plusView.visibility = View.VISIBLE
        }
        binding.viewFlexbox.plusView.setOnClickListener {
        }

        item.reactions.forEach { rec ->
            var count = rec.count
            val reactionView = ReactionView(viewMessageData.context).apply {
                setOnClickListener {
                    if (isSelected) {
                        if (count == 1) {
                            visibility = ViewGroup.GONE
                        }
                        count -= 1
                    } else {
                        count += 1
                    }
                    it.isSelected = !it.isSelected
                    setReactionCount(count)
                }
                setReaction(rec.emoji)
                setReactionCount(rec.count)
            }
            listViewReaction.add(reactionView)
        }
        binding.viewFlexbox.removeAllViewsExceptPlus()
        binding.viewFlexbox.addListBox(listViewReaction)
        if (listViewReaction.isNotEmpty()) {
            binding.viewFlexbox.plusView.visibility = View.VISIBLE
        }
    }
}
