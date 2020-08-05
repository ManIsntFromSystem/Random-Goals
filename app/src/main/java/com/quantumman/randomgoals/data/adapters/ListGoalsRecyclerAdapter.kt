package com.quantumman.randomgoals.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.GoalListNames


class ListGoalsRecyclerAdapter(private val context: Context,
                               private val goals: List<GoalListNames>,
                               onGoalListener: OnGoalListener
) :
    RecyclerView.Adapter<ListGoalsRecyclerAdapter.GoalsListViewHolder>() {
    private val mOnGoalListener: OnGoalListener = onGoalListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsListViewHolder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.item_list_goals,
                parent, false)
        return GoalsListViewHolder(view, mOnGoalListener)
    }

    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: GoalsListViewHolder, position: Int) {
        holder.iconItemGoalsList.setImageResource(goals[position].listIcon)
        holder.tvNameListGoal.text = goals[position].nameList
    }

    inner class GoalsListViewHolder(itemView: View, onGoalListener: OnGoalListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var iconItemGoalsList: ImageView = itemView.findViewById(R.id.iconItemGoalsList)
        var tvNameListGoal: TextView = itemView.findViewById(R.id.tvNameListGoal)
        private var mOnGoalListener: OnGoalListener = onGoalListener
        override fun onClick(view: View) {
            mOnGoalListener.onGoalClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnGoalListener{
        fun onGoalClick(position: Int)
    }
}