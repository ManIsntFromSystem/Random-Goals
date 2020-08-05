package com.quantumman.randomgoals.app.model

import com.quantumman.randomgoals.R

data class MyIcon(
    val iconName: Int,
    val iconCategory: String,
    var isSelected: Boolean = false
) {}

object IconsGoals {
    val ICONS_LIST = listOf(
        MyIcon(
            R.drawable.ic_purpose,
            "Default1"
        ),
        MyIcon(
            R.drawable.ic_goal_main_1,
            "Default2"
        ),
        MyIcon(
            R.drawable.ic_purpose_main,
            "Default3"
        ),
        MyIcon(
            R.drawable.ic_diskette_1,
            "Default4"
        ),
        MyIcon(
            R.drawable.ic_floppy_disk,
            "Default5"
        ),
        MyIcon(
            R.drawable.ic_floppy_disk_3,
            "Default6"
        ),
        MyIcon(
            R.drawable.ic_floppy_disk_2,
            "Default7"
        ),
        MyIcon(
            R.drawable.ic_notepad_2,
            "Default8"
        ),
        MyIcon(
            R.drawable.ic_edit_pencil,
            "Default9"
        )
    )
}