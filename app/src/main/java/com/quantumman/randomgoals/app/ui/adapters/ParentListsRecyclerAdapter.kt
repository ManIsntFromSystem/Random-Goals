package com.quantumman.randomgoals.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.ParentWithListGoals

class ParentListsRecyclerAdapter : RecyclerView.Adapter<ParentListsRecyclerAdapter.ParentViewHolder>() {
    private val parentLists: ArrayList<ParentWithListGoals> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_goals, parent, false)
        return ParentViewHolder(view)
    }

    override fun getItemCount() = parentLists.size

    fun updateData(list: List<ParentWithListGoals>) {
        notifyItemRangeRemoved(0, itemCount)
        parentLists.clear()
        parentLists.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(parentLists[position])
    }

    class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconItemGoalsList: ImageView = itemView.findViewById(R.id.iconItemGoalsList)
        private var tvNameListGoal: TextView = itemView.findViewById(R.id.tvNameListGoal)

        fun bind(parent: ParentWithListGoals) {
//            iconItemGoalsList.setImageResource(parent.parentIcon)
            tvNameListGoal.text = parent.parentName
        }
    }

    interface OnParentItemListener{
        fun onParentClick(position: Int)
    }
}