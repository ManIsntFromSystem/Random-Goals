package com.quantumman.randomgoals.app.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.IconsGoals
import com.quantumman.randomgoals.app.model.ParentWithListGoals

class ListGoalsDropDownAdapter(
    private val mContext: Context, private val parentLists: List<ParentWithListGoals>
) : ArrayAdapter<ParentWithListGoals>(mContext, 0, parentLists) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dropdownItemView = convertView

        if (dropdownItemView == null)
            dropdownItemView = LayoutInflater.from(mContext).inflate(R.layout.dropdown_layout_popup_item, parent,false)

        val parentItem = parentLists[position]

        val flagImage: ImageView = dropdownItemView!!.findViewById(R.id.ivIconDropDownItem)
        flagImage.setImageResource(IconsGoals.ICONS_LIST[parentItem.parentIcon].iconName)

        val langTextView: TextView = dropdownItemView.findViewById(R.id.tvListNameDropDownItem)
        langTextView.text = parentItem.parentName

        return dropdownItemView
    }
}