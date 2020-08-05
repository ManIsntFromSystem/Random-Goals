package com.quantumman.randomgoals.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.IconsGoals.ICONS_LIST
import com.quantumman.randomgoals.app.model.MyIcon
import kotlinx.android.synthetic.main.item_for_dialog_choose_icon.view.*

class IconsRecyclerAdapter(private val context: Context?,
                           private val icons: MutableList<MyIcon>,
                           selectedIcon: MyIcon,
                           onIconClickListener: OnIconClickListener
) :
    RecyclerView.Adapter<IconsRecyclerAdapter.IconsViewHolder>() {

    private var mOnClickListener: OnIconClickListener = onIconClickListener
    private var mSelectedIcon: MyIcon = selectedIcon
    private var prevIconPosition: Int = ICONS_LIST.indexOf(selectedIcon)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_for_dialog_choose_icon,
            parent, false
        )
        return IconsViewHolder(view, mOnClickListener)
    }

    override fun getItemCount() = icons.size

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        val currentIcon = icons[position]
        currentIcon.isSelected = currentIcon == mSelectedIcon
        holder.setData(currentIcon, position)
    }


    inner class IconsViewHolder(itemView: View, onClickListener: OnIconClickListener)
        : RecyclerView.ViewHolder(itemView){

        private lateinit var currentIcon: MyIcon
        private var currentPosition = 0

        init {
            itemView.relativeIconDialog.setOnClickListener {
                checkIsSelected()
                onClickListener.onIconChanged(currentIcon, currentPosition)
            }
        }

        fun setData(icon: MyIcon, position: Int) {
            this.currentIcon = icon
            this.currentPosition = position
            itemView.ivIconInDialog.setImageResource(icon.iconName)
            itemView.tvTagIconInDialog.text = icon.iconCategory
            itemView.ivChoseIcon.visibility = if (icon.isSelected) View.VISIBLE else View.INVISIBLE
        }

        private fun checkIsSelected() {
            if (currentIcon != mSelectedIcon) {
                notifyItemChanged(prevIconPosition)
                icons[currentPosition].isSelected = true
                notifyItemChanged(currentPosition)
                icons[currentPosition].isSelected = false
                prevIconPosition = currentPosition
                mSelectedIcon = currentIcon
            }
        }
    }

    interface OnIconClickListener {
        fun onIconChanged(icon: MyIcon?, position: Int)
    }
}