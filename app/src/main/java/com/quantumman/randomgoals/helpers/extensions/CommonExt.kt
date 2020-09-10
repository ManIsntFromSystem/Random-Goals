package com.quantumman.randomgoals.helpers.extensions

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor: SharedPreferences.Editor = edit()
    action(editor)
    editor.apply()
}

fun Activity.showToast(text: Any) = Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()
fun Activity.showSnack(text: String) = Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, message, length).show()

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(it) }
}