package com.quantumman.randomgoals.utils

import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.app.ui.adapters.ParentListsRecyclerAdapter

class SwipeListener(private val parentsAdapter: ParentListsRecyclerAdapter) : SwipeItemHelper.SwipeListener {
    override fun onSwipe(swipeViewHolder: RecyclerView.ViewHolder) {
        val adapterPosition = swipeViewHolder.adapterPosition
        if (adapterPosition != RecyclerView.NO_POSITION)
            parentsAdapter.removeItem(adapterPosition)
    }
}