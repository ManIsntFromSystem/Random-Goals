package com.quantumman.randomgoals.app.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.MyIcon
import com.quantumman.randomgoals.app.ui.adapters.IconsRecyclerAdapter


class ChooseIconDialogFragment() : DialogFragment() {
    private lateinit var dialogIconAdapter: IconsRecyclerAdapter
    private lateinit var recyclerDialogIcon: RecyclerView
    private lateinit var layout: RecyclerView.LayoutManager
    private lateinit var listener: ChoseIconInDialogListener
    private var currentIdList: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIdList = arguments?.getInt("currentIdList") ?: -1
        println("CurrentIdList in Dialog: $currentIdList")
    }

//    @SuppressLint("InflateParams")
//    private fun getIconView(): View {
//        val inflater: View = View.inflate(context, R.layout.dialog_choose_icon, null)
//        recyclerDialogIcon = inflater.findViewById(R.id.recyclerAllIcons)
//        layout = GridLayoutManager(this.activity, 3)
//        recyclerDialogIcon.layoutManager = layout
//        recyclerDialogIcon.setHasFixedSize(true)
//        dialogIconAdapter = IconsRecyclerAdapter(context, ICONS_LIST)
//        recyclerDialogIcon.adapter = dialogIconAdapter
//        return inflater
//    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity.let {
//            val builder = MaterialAlertDialogBuilder(requireContext())
//            builder.setView(getIconView())
//                .setPositiveButton(R.string.save) { dialog, id ->
//                    listener.onDialogPositiveButton(this)
//                }
//                .setNegativeButton(R.string.cancel) { dialog, id ->
//                    dialog.cancel()
//                }
//            builder.create()
//        }
//    }

    interface ChoseIconInDialogListener{
        fun onDialogPositiveButton(dialog: DialogFragment)
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
            MyIcon(R.drawable.ic_edit_pencil, "Default9")
        )
    }
}