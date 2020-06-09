package com.quantumman.randomgoals.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.model.Goal


class ItemsGoalAdapters(private val context: Context, private val goals: List<Goal>) :
    RecyclerView.Adapter<ItemsGoalAdapters.ItemsGoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsGoalViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_goal_edit,
            parent, false))

    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: ItemsGoalViewHolder, position: Int) {
        val currentGoal = goals[position]
        val imageResource: Int = context.resources
            .getIdentifier(currentGoal.iconGoal, "drawable", context.packageName)
        holder.nameItemGoalTxt.text = currentGoal.nameGoal
        holder.iconGoalImageView.setImageResource(imageResource)
    }

    class ItemsGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconGoalImageView: ImageView = itemView.findViewById(R.id.goalEditImageView)
        var nameItemGoalTxt: TextView = itemView.findViewById(R.id.nameItemGoalTxt)
    }
}