package com.quantumman.randomgoals.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.model.Goal
import com.quantumman.randomgoals.data.ListGoalsAdapter.GoalViewHolder

class ListGoalsAdapter(private val context: Context, private val goals: List<Goal>) :
    RecyclerView.Adapter<GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_list_goals,
            parent, false)
        return GoalViewHolder(view)
    }

    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val currentGoal = goals[position]
        val nameGoal = currentGoal.nameGoal
        val icon = currentGoal.iconGoal
        val imageResource = context.resources
            .getIdentifier(icon, "drawable", context.packageName)
        holder.nameItemGoalTxt.text = nameGoal
        holder.iconGoalImageView.setImageResource(imageResource)
    }

    inner class GoalViewHolder(itemView: View) : ViewHolder(itemView) {
        var iconGoalImageView: ImageView = itemView.findViewById(R.id.goalEditImageView)
        var nameItemGoalTxt: TextView = itemView.findViewById(R.id.nameItemGoalTxt)
    }
}