package com.github.lanalebedeva.mydiscord.channel.ui

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.channel.data.model.StreamData

class CustomizedAdapter(
    private val context: Context, private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, StreamData>
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]?.topics?.get(
            expandedListPosition
        ) ?: ""
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertViewIn: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertViewIn
        val expandedListText = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.topic_item_child_expandable_list, null)
        }
        val expandedListTextView = convertView
            ?.findViewById<View>(R.id.topic_item_child) as TextView
        expandedListTextView.text = "$expandedListText"
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]?.topics?.size ?: 0
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }


    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        listPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup
    ): View {
        var convertViewGetGroupView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertViewGetGroupView == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewGetGroupView =
                layoutInflater.inflate(R.layout.stream_item_for_expandable_list, null)
        }
        val listTitleTextView = convertViewGetGroupView
            ?.findViewById<View>(R.id.stream_item_group) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = context.getString(R.string.title_message, listTitle)
        if (isExpanded) {
            convertViewGetGroupView.findViewById<ImageView>(R.id.steam_item_img)
                .setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
        } else {
            convertViewGetGroupView.findViewById<ImageView>(R.id.steam_item_img)
                .setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
        }
        return convertViewGetGroupView
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
