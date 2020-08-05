package com.quantumman.randomgoals.data.adapters

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

class ItemsGoalAdapters(private val context: Context, goals: Array<Goal>, private var onDelListener: OnDeleteGoalListener) :
    RecyclerView.Adapter<ItemsGoalAdapters.ItemsGoalViewHolder>() {
    private val listGoals = goals.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsGoalViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_goal_edit,
                parent, false
            )
        )

    override fun getItemCount() = listGoals.size

    fun updateData(update: Array<Goal>) {
        listGoals.clear()
        listGoals.addAll(update)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemsGoalViewHolder, position: Int) {
        val currentGoal = listGoals[position]
        holder.nameItemGoalTxt.text = currentGoal.nameGoal
        holder.deleteItemGoalBtn.setOnClickListener {
            onDelListener.onDeleteListener(position)
            removeItem(position)
        }
    }

    class ItemsGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deleteItemGoalBtn: ImageView = itemView.findViewById(R.id.deleteItemGoalBtn)
        var nameItemGoalTxt: TextView = itemView.findViewById(R.id.nameItemGoalTxt)
    }

    interface OnDeleteGoalListener {
        fun onDeleteListener(position: Int)
    }

    private fun removeItem(position: Int) {
        val uri: Uri = ContentUris.withAppendedId(CONTENT_URI_GOAL, listGoals[position].id.toLong())
        GoalDBOpenHelper(context).delListGoalsByListName(uri, null, null)
        listGoals.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}