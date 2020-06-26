package com.quantumman.randomgoals.data.dialogIcons

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.views.ChooseIconDialogFragment
import com.quantumman.randomgoals.data.GoalsListAdapter
import com.quantumman.randomgoals.data.helpers.GoalsContract
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.CONTENT_URI_LIST

class DialogIconsRecyclerAdapter(private val context: Context?, icons: List<MyIcon>) :
    RecyclerView.Adapter<DialogIconsRecyclerAdapter.IconsViewHolder>() {
    private val myIconsList = icons.toMutableList()
    private var prevPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_for_dialog_choose_icon,
            parent, false
        )
        return IconsViewHolder(view)
    }

    override fun getItemCount() = myIconsList.size

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        val currentGoal = myIconsList[position]
        holder.ivIconInDialog.setImageResource(currentGoal.iconName)
        holder.tvTagIconInDialog.text = currentGoal.iconCategory
        holder.ivChoseIcon.visibility = if (currentGoal.isSelected) View.VISIBLE else View.INVISIBLE
        myIconsList[prevPosition].isSelected = false
        holder.relativeIconDialog.setOnClickListener { checkIsSelected(position) }
    }

    private fun checkIsSelected(position: Int) {
        if (prevPosition >= 0) {
            myIconsList[prevPosition].isSelected = false
            notifyItemChanged(prevPosition)
        }
        myIconsList[position].isSelected = true
        notifyItemChanged(position)
        prevPosition = position
    }

    class IconsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relativeIconDialog: RelativeLayout = itemView.findViewById(R.id.relativeIconDialog)
        var ivIconInDialog: ImageView = itemView.findViewById(R.id.ivIconInDialog)
        var ivChoseIcon: ImageView = itemView.findViewById(R.id.ivChoseIcon)
        var tvTagIconInDialog: TextView = itemView.findViewById(R.id.tvTagIconInDialog)
    }

    interface SelectedIconListener {
        fun onSelectedIdIcon(id: Int)
    }
}

@SuppressLint("DefaultLocale")
private fun String.toNormalText() = this.replace("ic_", "").capitalize()