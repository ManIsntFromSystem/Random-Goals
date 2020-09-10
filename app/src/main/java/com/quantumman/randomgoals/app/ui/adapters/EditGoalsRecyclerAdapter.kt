package com.quantumman.randomgoals.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.GoalItem

class GoalItemsRecyclerAdapter : RecyclerView.Adapter<GoalItemsRecyclerAdapter.ItemsGoalViewHolder>() {
    private val listGoals: ArrayList<GoalItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsGoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal_edit, parent, false)
        return ItemsGoalViewHolder(view)
    }

    override fun getItemCount() = listGoals.size

    fun updateData(list: Array<GoalItem>) {
        listGoals.clear()
        listGoals.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemsGoalViewHolder, position: Int) {
        val currentGoal = listGoals[position]
        holder.nameItemGoalTxt.text = currentGoal.goalName
        holder.deleteItemGoalBtn.setOnClickListener {
//            onDelListener.onDeleteListener(position)
//            removeItem(position)
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
        listGoals.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}