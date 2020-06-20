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
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.data.helpers.GoalDBOpenHelper

class ItemsGoalAdapters(private val context: Context, goals: Array<Goal>) :
    RecyclerView.Adapter<ItemsGoalAdapters.ItemsGoalViewHolder>() {
    private val listGoals = goals.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsGoalViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_goal_edit,
            parent, false
        ))

    override fun getItemCount() = listGoals.size

    fun updateData(update: Array<Goal>) {
        listGoals.clear()
        listGoals.addAll(update)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemsGoalViewHolder, position: Int) {
        val currentGoal = listGoals[position]
        //saved for future
        //val imageResource: Int = context.resources
        //    .getIdentifier("icon", "drawable", context.packageName)
        holder.nameItemGoalTxt.text = currentGoal.nameGoal
        println("Position: $position")
        holder.deleteItemGoalBtn.setOnClickListener { removeItem(position) }
    }

    class ItemsGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deleteItemGoalBtn: ImageView = itemView.findViewById(R.id.deleteItemGoalBtn)
        var nameItemGoalTxt: TextView = itemView.findViewById(R.id.nameItemGoalTxt)
    }

    private fun removeItem(position: Int) {
        val uri: Uri = ContentUris.withAppendedId(CONTENT_URI_GOAL, listGoals[position].id.toLong())
        listGoals.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        GoalDBOpenHelper(context)
            .delListGoalsByListName(uri, null, null)
    }
}