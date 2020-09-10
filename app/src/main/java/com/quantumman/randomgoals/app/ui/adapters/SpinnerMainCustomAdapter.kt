package com.quantumman.randomgoals.app.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.quantumman.randomgoals.app.model.Goal

class SpinnerMainCustomAdapter(
    ctx: Context,
    moods: List<Goal>
) :
    ArrayAdapter<Goal>(ctx, 0, moods) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
//        val mood = getItem(position)
//        val view = recycledView ?: LayoutInflater.from(context).inflate(
//            R.layout.activity_list_item,
//            parent,
//            false
//        )
//        view.moodImage.setImageResource(mood.image)
//        view.moodText.text = mood.description
        return View(context)
    }
}
