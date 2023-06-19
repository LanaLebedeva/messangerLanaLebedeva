package com.github.lanalebedeva.mydiscord.tirecycle

import android.view.View
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview.generatorEmoji.GenerateEmoji
import com.github.lanalebedeva.mydiscord.messages.data.model.Reactions
import com.github.lanalebedeva.mydiscord.tirecycle.action.CustomClick
import com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview.FlexBoxLayout
import com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview.ReactionView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_rx2.actions.TiRecyclerCustomActionObservable


class CustomClickObservable : TiRecyclerCustomActionObservable<View, CustomClick>() {
    override fun accept(holder: BaseViewHolder<*>, view: View) {
        view.setOnClickListener {
            initBottomShitDialog(GenerateEmoji.generateListReaction(), view)
        }
    }

    private fun initBottomShitDialog(listReaction: List<Reactions>, view: View) {
        val bottomSheetDialog =
            BottomSheetDialog(view.context, R.style.BottomSheetDialog)
        val listReactionView = mutableListOf<ReactionView>()
        val reaction = view as FlexBoxLayout
        listReaction.forEach { reactionIt ->
            listReactionView.add(ReactionView(view.context).apply {
                setReaction(reactionIt.emoji)
                isSelected = false
                setOnClickListener {
                    reaction.plusView.visibility = View.VISIBLE
                    reaction.addBox(
                        ReactionView(context).apply {
                            setReaction(reactionIt.emoji)
                            setReactionCount(1)
                            isSelected = true
                            setOnClickListener {
                                setReactionCount(0)
                                visibility = View.GONE
                            }
                        }
                    )
                    bottomSheetDialog.dismiss()
                }
                setReactionCount(0)

            })
        }
        val flexBoxLayout = FlexBoxLayout(view.context)
        flexBoxLayout.addListBox(listReactionView)
        bottomSheetDialog.setContentView(flexBoxLayout)
        bottomSheetDialog.show()
        return
    }
}
