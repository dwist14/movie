@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.purwoko.movie.common

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import android.widget.Toast.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

@PublishedApi
internal inline fun Context.make(message: CharSequence, duration: Int): Toast = makeText(this, message, duration)

@PublishedApi
internal inline fun Context.make(@StringRes message: Int, duration: Int): Toast = makeText(this, message, duration)

inline fun Context.toast(message: CharSequence, length: Int = LENGTH_SHORT): Toast = make(message, length).apply { show() }
inline fun Fragment.toast(message: CharSequence): Toast? = activity?.toast(message)
inline fun Dialog.toast(message: CharSequence): Toast = context.toast(message)

inline fun Context.toast(@StringRes message: Int): Toast = make(message, LENGTH_SHORT).apply { show() }
inline fun Fragment.toast(@StringRes message: Int): Toast? = activity?.toast(message)
inline fun Dialog.toast(@StringRes message: Int): Toast = context.toast(message)

inline fun Context.longToast(message: CharSequence): Toast = make(message, LENGTH_LONG).apply { show() }
inline fun Fragment.longToast(message: CharSequence): Toast? = activity?.longToast(message)
inline fun Dialog.longToast(message: CharSequence): Toast = context.longToast(message)

inline fun Context.longToast(@StringRes message: Int): Toast = make(message, LENGTH_LONG).apply { show() }
inline fun Fragment.longToast(@StringRes message: Int): Toast? = activity?.longToast(message)
inline fun Dialog.longToast(@StringRes message: Int): Toast = context.longToast(message)