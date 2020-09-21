package com.quantumman.randomgoals.app.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.quantumman.randomgoals.app.model.ParentWithListGoals

class ParentListsDiffCallback : DiffUtil.ItemCallback<ParentWithListGoals>() {

    override fun areItemsTheSame(oldItem: ParentWithListGoals, newItem: ParentWithListGoals): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ParentWithListGoals, newItem: ParentWithListGoals): Boolean {
        return oldItem.parentDate == newItem.parentDate
    }
}