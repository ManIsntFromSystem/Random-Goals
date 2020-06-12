package com.quantumman.randomgoals.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.model.Goal
import com.quantumman.randomgoals.util.listen


class ItemsGoalAdapters(private val context: Context, private val goals: List<Goal>) :
    RecyclerView.Adapter<ItemsGoalAdapters.ItemsGoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsGoalViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_goal_edit,
            parent, false
        )
        return ItemsGoalViewHolder(view).listen { position, type ->
            val item = goals[position]
            Log.d("My", "Item: $item ------ Type: $type")
        }
    }

    fun getts(){
        Log.d("My", "Item: item ------ Type: type")
    }
    override fun getItemCount() = goals.size

    override fun onBindViewHolder(holder: ItemsGoalViewHolder, position: Int) {
        val currentGoal = goals[position]
        val imageResource: Int = context.resources
            .getIdentifier(currentGoal.iconGoal, "drawable", context.packageName)
        holder.nameItemGoalTxt.text = currentGoal.nameGoal
        holder.iconGoalImageView.setImageResource(imageResource)
        holder.deleteItemGoalBtn.setOnClickListener {
            val uri: Uri = ContentUris.withAppendedId(GoalsContract.MemberEntry.CONTENT_URI,
                goals[position].id.toLong())
            GoalDBOpenHelper(context).delListGoalsByListName(uri, null, null)
            notifyDataSetChanged()
        }
    }

    class ItemsGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconGoalImageView: ImageView = itemView.findViewById(R.id.goalEditImageView)
        var deleteItemGoalBtn: ImageView = itemView.findViewById(R.id.deleteItemGoalBtn)
        var nameItemGoalTxt: TextView = itemView.findViewById(R.id.nameItemGoalTxt)
    }
}