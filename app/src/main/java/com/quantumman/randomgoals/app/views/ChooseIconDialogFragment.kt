package com.quantumman.randomgoals.app.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.dialogIcons.DialogIconsRecyclerAdapter
import com.quantumman.randomgoals.data.dialogIcons.MyIcon
import java.lang.Exception


class ChooseIconDialogFragment(): DialogFragment() {
    private lateinit var dialogIconAdapter: DialogIconsRecyclerAdapter
    private lateinit var recyclerDialogIcon: RecyclerView
    private lateinit var layout: RecyclerView.LayoutManager

    @SuppressLint("InflateParams")
    private fun getIconView(): View {
        try {
            val inflater: View = View.inflate(context, R.layout.dialog_choose_icon, null)
            recyclerDialogIcon = inflater.findViewById(R.id.recyclerAllIcons)
            layout = GridLayoutManager(this.activity, 3)
            recyclerDialogIcon.layoutManager = layout
            recyclerDialogIcon.setHasFixedSize(true)
            dialogIconAdapter = DialogIconsRecyclerAdapter(context, ICONS_LIST)
            recyclerDialogIcon.adapter = dialogIconAdapter
            return inflater
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return View.inflate(context, R.layout.dialog_choose_icon, null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle(R.string.dialog_choose_icons)
                .setView(getIconView())
                .setIcon(R.drawable.ic_purpose)
                .setPositiveButton(R.string.save) { dialog, id ->
                    println("$dialog ------  $id")
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        }
    }

    companion object {
        val ICONS_LIST = listOf(
            MyIcon(R.drawable.ic_purpose, "Default1"),
            MyIcon(R.drawable.ic_goal_main_1, "Default2"),
            MyIcon(R.drawable.ic_purpose_main, "Default3"),
            MyIcon(R.drawable.ic_diskette_1, "Default4"),
            MyIcon(R.drawable.ic_floppy_disk, "Default5"),
            MyIcon(R.drawable.ic_floppy_disk_3, "Default6"),
            MyIcon(R.drawable.ic_floppy_disk_2, "Default7"),
            MyIcon(R.drawable.ic_notepad_2, "Default8"),
            MyIcon(R.drawable.ic_edit_pencil, "Default9"),
            MyIcon(R.drawable.ic_purpose, "Default1"),
            MyIcon(R.drawable.ic_goal_main_1, "Default2"),
            MyIcon(R.drawable.ic_purpose_main, "Default3"),
            MyIcon(R.drawable.ic_diskette_1, "Default4"),
            MyIcon(R.drawable.ic_floppy_disk, "Default5"),
            MyIcon(R.drawable.ic_floppy_disk_3, "Default6"),
            MyIcon(R.drawable.ic_floppy_disk_2, "Default7"),
            MyIcon(R.drawable.ic_notepad_2, "Default8"),
            MyIcon(R.drawable.ic_edit_pencil, "Default9"),
            MyIcon(R.drawable.ic_wishlist, "Default10"))
    }
}