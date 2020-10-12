package com.quantumman.randomgoals.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.IconsGoals.ICONS_LIST
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import kotlinx.android.synthetic.main.item_parent_list.view.*
import timber.log.Timber

class ParentListsRecyclerAdapter
    : ListAdapter<ParentWithListGoals, ParentListsRecyclerAdapter.ParentViewHolder>(
    ParentListsDiffCallback()
) {
    var onItemClick: ((ParentWithListGoals) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent_list, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun removeItem(position: Int) {
        Timber.e("Remove item pos:$position")
    }

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconItemGoalsList: ImageView = itemView.iconItemGoalsList
        private var tvNameListGoal: TextView = itemView.tvNameListGoal

        fun bind(parent: ParentWithListGoals) {
            iconItemGoalsList.setImageResource(ICONS_LIST[parent.parentIcon].iconName)
            tvNameListGoal.text = parent.parentName
        }

        init {
            itemView.setOnClickListener { onItemClick?.invoke(getItem(adapterPosition)) }
        }

    }
}