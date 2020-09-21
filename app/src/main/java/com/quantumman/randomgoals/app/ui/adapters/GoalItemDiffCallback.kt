package com.quantumman.randomgoals.app.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.quantumman.randomgoals.app.model.GoalItem

class GoalItemDiffCallback : DiffUtil.ItemCallback<GoalItem>() {

    override fun areItemsTheSame(oldItem: GoalItem, newItem: GoalItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GoalItem, newItem: GoalItem): Boolean {
        return oldItem.goalDate == newItem.goalDate
    }
}