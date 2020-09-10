package com.quantumman.randomgoals.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.IconsGoals.ICONS_LIST
import com.quantumman.randomgoals.app.model.MyIcon
import com.quantumman.randomgoals.app.ui.adapters.IconsRecyclerAdapter


class ChoosingNewIconForListName : AppCompatActivity(), IconsRecyclerAdapter.OnIconClickListener {

    private lateinit var recyclerDialogIcon: RecyclerView
    private lateinit var selectedIcon: MyIcon
    private var selectedIconPosition: Int = 0
    private var intentCurrentIcon:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosing_new_icon_for_list_name)
        if (intent != null){
            intentCurrentIcon = intent.getIntExtra("currentIconName", R.drawable.ic_purpose)
            selectedIcon = ICONS_LIST.first { it.iconName == intentCurrentIcon }
        }
        selectedIconPosition = ICONS_LIST.indexOf(selectedIcon)
        createRecycler()
    }

    fun onSaveNewIcon(view: View) {
        println("Intent Icon: $intentCurrentIcon")
        println("Selected Icon: $selectedIcon")
        val name = selectedIcon.iconName
        if (intentCurrentIcon != name) {
            val intent = Intent()
            intent.putExtra("selectedIconName", name)
            setResult(RESULT_OK, intent)
        }
        finishAfterTransition()
    }

    private fun createRecycler() {
        recyclerDialogIcon = findViewById(R.id.recyclerAllIcons)
        val list = ICONS_LIST.toMutableList()
        val dialogIconAdapter =
            IconsRecyclerAdapter(
                this,
                list,
                selectedIcon,
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
        selectedIcon = icon!!
    }
}