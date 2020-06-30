package com.quantumman.randomgoals.app.model

import java.io.Serializable

data class ListNames (
    val id: Int = -1,
    val nameList: String = "",
    var listIcon: Int = -1
) : Serializable {}