package com.quantumman.randomgoals.app.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.dialogIcons.DialogIconsRecyclerAdapter
import com.quantumman.randomgoals.data.dialogIcons.MyIcon


class ChoosingNewIconForListName : AppCompatActivity() {

    private lateinit var recyclerDialogIcon: RecyclerView
    private var intentCurrentIdIcon = -1
    private var selectedIdIcon = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosing_new_icon_for_list_name)

        checkIntent()
        recyclerDialogIcon = findViewById(R.id.recyclerAllIcons)
        createRecycler()
    }

    private fun checkIntent() {

    }

    private fun receiveNewIcon() {
        val intent = Intent()
        intent.putExtra("item", selectedIdIcon)
        setResult(RESULT_OK, intent)
    }

    private fun createRecycler() {
        val dialogIconAdapter = DialogIconsRecyclerAdapter(this, ICONS_LIST)
        val layout = GridLayoutManager(this, 3)
        recyclerDialogIcon.apply {
            layoutManager = layout
            setHasFixedSize(true)
            adapter = dialogIconAdapter
        }
    }

    fun onSaveNewIcon(view: View) {

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
            MyIcon(R.drawable.ic_wishlist, "Default10")
        )
    }
}