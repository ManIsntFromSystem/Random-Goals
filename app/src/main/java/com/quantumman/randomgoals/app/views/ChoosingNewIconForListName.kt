package com.quantumman.randomgoals.app.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.IconsGoals.ICONS_LIST
import com.quantumman.randomgoals.app.model.MyIcon
import com.quantumman.randomgoals.data.IconsRecyclerAdapter


class ChoosingNewIconForListName : AppCompatActivity(), IconsRecyclerAdapter.OnIconClickListener {

    private lateinit var recyclerDialogIcon: RecyclerView
    private var intentCurrentIdIcon = -1
    private var selectedIdIcon = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosing_new_icon_for_list_name)
        if (intent != null){
            intentCurrentIdIcon = intent.getIntExtra("currentIconName", R.drawable.ic_purpose)
            selectedIdIcon = intentCurrentIdIcon
        }
        println("Intent for Edit: $intentCurrentIdIcon")
        println("Drawable: ${R.drawable.ic_purpose}")
        createRecycler()
    }

    fun onSaveNewIcon(view: View) {
        println("Intent Icon: $intentCurrentIdIcon")
        println("Selected Icon: $selectedIdIcon")
        if (intentCurrentIdIcon != selectedIdIcon) {
            val intent = Intent()
            intent.putExtra("selectedIconName", selectedIdIcon)
            setResult(RESULT_OK, intent)
        }
        finish()
    }

    private fun createRecycler() {
        recyclerDialogIcon = findViewById(R.id.recyclerAllIcons)
        val dialogIconAdapter =
            IconsRecyclerAdapter(
                this,
                ICONS_LIST,
                this
            )
        val layout = GridLayoutManager(this, 4)
        recyclerDialogIcon.apply {
            layoutManager = layout
            setHasFixedSize(true)
            adapter = dialogIconAdapter
        }
    }

    override fun onIconChanged(icon: MyIcon?, position: Int) {
        selectedIdIcon = ICONS_LIST[position].iconName
        println("Override OnSelectedIcon: $icon")
        println("Override OnSelectedIcon: ${ICONS_LIST[position]}")
    }
}