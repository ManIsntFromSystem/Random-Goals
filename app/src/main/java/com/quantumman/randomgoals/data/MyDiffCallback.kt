package com.quantumman.randomgoals.data

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.quantumman.randomgoals.model.Goal

class MyDiffCallback(newPersons: List<Goal>?, oldPersons: List<Goal>?): DiffUtil.Callback() {

    private var oldPersons: List<Goal>? = null
    private var newPersons: List<Goal>? = null

    override fun getOldListSize(): Int {
        return oldPersons!!.size
    }

    override fun getNewListSize(): Int {
        return newPersons!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPersons!![oldItemPosition].id == newPersons!![newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPersons!![oldItemPosition] == newPersons!![newItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}