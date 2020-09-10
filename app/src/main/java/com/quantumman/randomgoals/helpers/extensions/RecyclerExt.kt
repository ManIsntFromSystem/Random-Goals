package com.quantumman.randomgoals.helpers.extensions

import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Long) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemId)
    }
    return this
}