package com.quantumman.randomgoals.data.dialogIcons

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.Goal

class DialogIconsRecyclerAdapter(private val context: Context?,  icons: List<MyIcon>) :
    RecyclerView.Adapter<DialogIconsRecyclerAdapter.IconsViewHolder>() {
    private val myIconsList = icons.toMutableList()
    private var prevPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IconsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_for_dialog_choose_icon,
                parent, false
            )
        )

    override fun getItemCount() = myIconsList.size

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        val currentGoal = myIconsList[position]
        println("Name: ${currentGoal.iconName} -- category: ${currentGoal.iconCategory} -- check: ${currentGoal.isSelected}")
        holder.ivIconInDialog.setImageResource(currentGoal.iconName)
        holder.tvTagIconInDialog.text = currentGoal.iconCategory
        if (currentGoal.isSelected) holder.ivChoseIcon.setImageResource(R.drawable.ic_checked)
        else holder.ivChoseIcon.visibility = View.GONE
        holder.relativeIconDialog.setOnClickListener {
            checkIsSelected(position)
            holder.ivChoseIcon.setImageResource(R.drawable.ic_checked)
            holder.ivChoseIcon.visibility = View.VISIBLE        }
    }

    private fun checkIsSelected(position: Int) {
        if (prevPosition >= 0) {
            myIconsList[prevPosition].isSelected = false
            notifyItemChanged(prevPosition)
        }
        prevPosition = position
    }

    class IconsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relativeIconDialog: RelativeLayout = itemView.findViewById(R.id.relativeIconDialog)
        var ivIconInDialog: ImageView = itemView.findViewById(R.id.ivIconInDialog)
        var ivChoseIcon: ImageView = itemView.findViewById(R.id.ivChoseIcon)
        var tvTagIconInDialog: TextView = itemView.findViewById(R.id.tvTagIconInDialog)
    }
}

@SuppressLint("DefaultLocale")
private fun String.toNormalText() = this.replace("ic_", "").capitalize()