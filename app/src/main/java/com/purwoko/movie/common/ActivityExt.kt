@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.purwoko.movie.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ActivityCompat

inline val Activity.act: Context
    get() = this

/**
 * @param activity
 * @param id
 * @return int from resources by id, more concretely from dimens.xml file.
 */
fun Activity.getIntFromRes(id: Int): Int {
    val resources = this.resources ?: return 0
    return resources.getInteger(id)
}

inline fun Activity.hideInputMethod() {
    inputMethodManager.hideSoftInputFromWindow(window.peekDecorView().windowToken, 0)
}

inline fun Activity.hideInputMethod(v : View) {
    val imm: InputMethodManager = v.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(
        v.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )

    hideInputMethod()
}

inline fun Activity.showInputMethod(v: EditText) {
    v.requestFocus()
    inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED)
}

inline fun Activity.supportFinishAfterTransition() {
    ActivityCompat.finishAfterTransition(this)
}

inline fun Activity.supportFinishAffinity() {
    ActivityCompat.finishAffinity(this)
}

inline fun Activity.finishWithResult(resultCode: Int, data: Intent) {
    setResult(resultCode, data)
    finish()
}

// LOCK ORIENTATION

inline fun Activity.lockCurrentScreenOrientation(orientation: Int = resources.configuration.orientation) {
    requestedOrientation = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        else -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }
}

inline fun Activity.unlockScreenOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

inline fun <T> Activity.extra(key: String): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        @Suppress("UNCHECKED_CAST")
        intent?.extras?.get(key) as T
    }
}

inline fun <T> Activity.extraOrNull(key: String): Lazy<T?> {
    return lazy(LazyThreadSafetyMode.NONE) {
        @Suppress("UNCHECKED_CAST")
        intent?.extras?.get(key) as? T?
    }
}
