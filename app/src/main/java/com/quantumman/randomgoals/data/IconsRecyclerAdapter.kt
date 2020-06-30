package com.quantumman.randomgoals.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.MyIcon
import kotlinx.android.synthetic.main.item_for_dialog_choose_icon.view.*

class IconsRecyclerAdapter(private val context: Context?,
                           private val icons: List<MyIcon>,
                            onIconClickListener: OnIconClickListener) :
    RecyclerView.Adapter<IconsRecyclerAdapter.IconsViewHolder>() {
    private var mOnClickListener: OnIconClickListener = onIconClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_for_dialog_choose_icon,
            parent, false
        )
        return IconsViewHolder(view, mOnClickListener)
    }

    override fun getItemCount() = icons.size

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        val currentGoal = icons[position]
        holder.setData(currentGoal, position)
    }


    inner class IconsViewHolder(
        itemView: View, onClickListener: OnIconClickListener)
        : RecyclerView.ViewHolder(itemView){

        private var currentIcon: MyIcon? = null
        private var currentPosition = 0
        private var prevPosition = 0

        init {
            itemView.relativeIconDialog.setOnClickListener {
                println("Current Icon: $currentIcon --- CurrentPos: $currentPosition")
                checkIsSelected(currentPosition)
                onClickListener.onIconChanged(currentIcon, currentPosition)
            }
        }

        fun setData(icon: MyIcon?, position: Int) {
            this.currentIcon = icon
            this.currentPosition = position
            itemView.ivIconInDialog.setImageResource(icon!!.iconName)
            itemView.tvTagIconInDialog.text = icon.iconCategory
            itemView.ivChoseIcon.visibility = if (icon.isSelected) View.VISIBLE else View.INVISIBLE
            icons[position].isSelected = false
        }

        private fun checkIsSelected(position: Int) {
            if (prevPosition != position) notifyItemChanged(prevPosition)
            icons[position].isSelected = true
            notifyItemChanged(position)
            prevPosition = position
        }
    }

    interface OnIconClickListener {
        fun onIconChanged(icon: MyIcon?, position: Int)
    }
}