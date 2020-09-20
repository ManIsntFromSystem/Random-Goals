package com.quantumman.randomgoals.helpers.extensions

fun String.incrementDefaultName(): String = when {
    !"""\(+\d.*\)+""".toRegex().containsMatchIn(this) -> "$this(2)"
    else -> {
        val currentNumber = this.replace("""\D""".toRegex(), "")
            .replace("(", "")
            .replace(")", "")

        val incNumber = "(${currentNumber.toInt() + 1})"
        this.replace("""\(+\d.*\)+""".toRegex(), incNumber)
    }
}

