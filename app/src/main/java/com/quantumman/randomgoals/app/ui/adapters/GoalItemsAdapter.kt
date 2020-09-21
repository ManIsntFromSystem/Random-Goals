package com.quantumman.randomgoals.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.helpers.enums.RecyclerClickType
import com.quantumman.randomgoals.helpers.extensions.toEditable
import kotlinx.android.synthetic.main.item_goal_edit.view.*

class GoalItemsAdapter : ListAdapter<GoalItem, GoalItemsAdapter.GoalItemsViewHolder>(
    GoalItemDiffCallback()
) {

    //transferring the call to the fragment | its not best solution, but work pretty good
    var onItemClick: ((GoalItem, Int, RecyclerClickType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal_edit, parent, false)
        return GoalItemsViewHolder(view)
    }

    // DiffUtil.ItemCallback in all its beauty
    override fun onBindViewHolder(holder: GoalItemsViewHolder, position: Int) = holder.bind(getItem(position))

    inner class GoalItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deleteItemGoalBtn: ImageView = itemView.deleteItemGoalBtn
        private val nameItemGoalTxt: TextView = itemView.nameItemGoalTxt

        fun bind(goalItem: GoalItem) { nameItemGoalTxt.text = goalItem.goalName.toEditable() }

        //handling View type pressed
        init {
            deleteItemGoalBtn.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition), adapterPosition, RecyclerClickType.DELETE)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition), adapterPosition, RecyclerClickType.EDIT_NAME)
            }
        }
    }
}