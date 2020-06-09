package com.quantumman.randomgoals.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.model.Goal


class GoalsListAdapter(private val context: Context,
                       private val goals: List<String>,
                       onGoalListener: OnGoalListener) :
    RecyclerView.Adapter<GoalsListAdapter.GoalsListViewHolder>() {
    private val mOnGoalListener: OnGoalListener = onGoalListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsListViewHolder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.item_list_goals,
                parent, false)
            println(goals)
        return GoalsListViewHolder(view, mOnGoalListener)
    }

    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: GoalsListViewHolder, position: Int) {
        holder.tvNameListGoal.text = goals.elementAt(position)
    }

    inner class GoalsListViewHolder(itemView: View, onGoalListener: OnGoalListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvNameListGoal: TextView = itemView.findViewById(R.id.tvNameListGoal)
        private var mOnGoalListener: OnGoalListener = onGoalListener
        override fun onClick(view: View) {
            Log.d("TAG", "onClick: $adapterPosition")
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