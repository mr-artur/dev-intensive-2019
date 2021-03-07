package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.R

fun Activity.getRootView() : View = findViewById(R.id.content)

fun Activity.hideKeyboard() {
    val windowToken = getRootView().windowToken
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val visibleBounds = Rect()
    getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    return heightDiff > 0 //https://proandroiddev.com/how-to-detect-if-the-android-keyboard-is-open-269b255a90f5
}

fun Activity.isKeyboardClosed() : Boolean = !isKeyboardOpen()