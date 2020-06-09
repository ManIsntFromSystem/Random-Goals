package com.quantumman.randomgoals.model

class Goal {
    var id: Int = 0
    var nameListGoals = ""
    var nameGoal: String = ""
    var iconGoal: String = ""

    constructor(id: Int, nameListGoals: String, nameGoal: String, iconGoal: String){
        this.id = id
        this.nameListGoals = nameListGoals
        this.nameGoal = nameGoal
        this.iconGoal = iconGoal
    }

    constructor() {}
}