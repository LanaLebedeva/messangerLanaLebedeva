package com.github.lanalebedeva.mydiscord.tirecycle.viewholder

import android.view.View
import com.github.lanalebedeva.mydiscord.databinding.DataItemBinding
import com.github.lanalebedeva.mydiscord.messages.data.model.DateData
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class DateDataViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<DateData>(view, clicks) {

    private val binding = DataItemBinding.bind(view)

    override fun bind(item: DateData) {
        binding.dataItemTextView.text = item.date
    }
}
