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
import com.quantumman.randomgoals.data.helpers.GoalsContract
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL

class DialogIconsRecyclerAdapter(private val context: Context?,
                                 icons: List<MyIcon>,
                                 private val currentId: String) :
    RecyclerView.Adapter<DialogIconsRecyclerAdapter.IconsViewHolder>(),
    ChooseIconDialogFragment.ChoseIconInDialogListener{
    private val myIconsList = icons.toMutableList()
    private var prevPosition = -1
    private lateinit var listener: IntentInterface
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
        holder.ivIconInDialog.setImageResource(currentGoal.iconName)
        holder.tvTagIconInDialog.text = currentGoal.iconCategory
        holder.ivChoseIcon.visibility = if (currentGoal.isSelected) View.VISIBLE else View.INVISIBLE
        holder.relativeIconDialog.setOnClickListener {
            checkIsSelected(position)
        }
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

    override fun onDialogPositiveButton(dialog: DialogFragment) {
        ContentValues().apply {
            put(COLUMN_ICON_GOAL, prevPosition)
            val uri = Uri.withAppendedPath(GoalsContract.GoalsListEntry.CONTENT_URI_LIST, currentId)
            val up = context?.contentResolver?.update(uri, this, null, null)
            if (up == 0) Log.d("MyLog", "Updated of data in table failed")
            else Log.d("MyLog", "Updating is successfully")
        }
    }

    interface IntentInterface {
        fun onSaveUpdatedIcon(context: Context?, id: Int)
    }
}

@SuppressLint("DefaultLocale")
private fun String.toNormalText() = this.replace("ic_", "").capitalize()