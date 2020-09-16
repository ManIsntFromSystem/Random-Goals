package com.quantumman.randomgoals.app.model

import android.os.Parcel
import android.os.Parcelable

data class ParentWithListGoals (
    val parentId: Long,
    var parentDate: Long,
    var parentName: String,
    var parentIcon: String,
    val listGoals: List<GoalItem>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.createTypedArray(GoalItem)?.toList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(parentId)
        parcel.writeLong(parentDate)
        parcel.writeString(parentName)
        parcel.writeString(parentIcon)
        parcel.writeTypedList(listGoals)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ParentWithListGoals> {
        override fun createFromParcel(parcel: Parcel): ParentWithListGoals = ParentWithListGoals(parcel)
        override fun newArray(size: Int): Array<ParentWithListGoals?> = arrayOfNulls(size)
    }
}

data class GoalItem(
    val goalId: Long,
    var goalDate: Long,
    val goalName: String,
    val parentList: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()?:"",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(goalId)
        parcel.writeLong(goalDate)
        parcel.writeString(goalName)
        parcel.writeLong(parentList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalItem> {
        override fun createFromParcel(parcel: Parcel): GoalItem {
            return GoalItem(parcel)
        }

        override fun newArray(size: Int): Array<GoalItem?> {
            return arrayOfNulls(size)
        }
    }
}