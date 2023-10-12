@file:Suppress("unused")

package com.purwoko.movie.common

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.*
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityManagerCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat


fun Context.getLifecycleFromContext(): Lifecycle? {
    return when (this) {
        is FragmentActivity -> this.lifecycle
        is ContextWrapper -> (this.baseContext as? FragmentActivity)?.lifecycle
        else -> null
    }
}


/**
 * Exactly whether a device is low-RAM is ultimately up to the device configuration, but currently
 * it generally means something in the class of a 512MB device with about a 800x480 or less screen.
 * This is mostly intended to be used by apps to determine whether they should
 * turn off certain features that require more RAM.
 *
 * @return true if this is a low-RAM device.
 */
fun Context?.isLowRamDevice(): Boolean {
    val activityManager = this?.getSystemService<ActivityManager>()
    return if (activityManager != null)
        ActivityManagerCompat.isLowRamDevice(activityManager)
    else false
}

inline fun <reified T> Context.getSystemService(): T? =
    ContextCompat.getSystemService(this, T::class.java)

fun Context.getActivityPendingIntent(
    requestCode: Int = 0,
    intent: Intent,
    flags: Int = PendingIntent.FLAG_ONE_SHOT
): PendingIntent =
    PendingIntent.getActivity(this, requestCode, intent, flags)

fun Context.getBroadcastPendingIntent(
    requestCode: Int = 0,
    intent: Intent,
    flags: Int = PendingIntent.FLAG_ONE_SHOT
): PendingIntent =
    PendingIntent.getBroadcast(this, requestCode, intent, flags)

fun Context.inflaterView(@LayoutRes layoutRes: Int, parent: ViewGroup? = null): View =
    View.inflate(this, layoutRes, parent)


fun Context.callPhone(phoneNumber: String) {
    val i = getCallIntent(phoneNumber)
    tryHandleIntent(i)
}

fun getCallIntent(phoneNumber: String): Intent {
    val intent = Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber"))
    return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}


private fun Context.tryHandleIntent(intent: Intent): Boolean {
    if (canHandleIntent(intent)) {
        startActivity(intent)
        return true
    }
    return false
}

private fun Context.canHandleIntent(intent: Intent) =
    packageManager.queryIntentActivities(intent, 0).size > 0

private fun Context.applyDimension(unit: Int, number: Number) =
    TypedValue.applyDimension(unit, number.toFloat(), resources.displayMetrics)

fun Context.dpToPx(dp: Number) = applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp)
fun Context.pxToDp(px: Number) = applyDimension(TypedValue.COMPLEX_UNIT_PX, px)
fun Context.spToPx(sp: Int) = applyDimension(TypedValue.COMPLEX_UNIT_PX, sp)
fun Context.pxToSp(px: Int) = applyDimension(TypedValue.COMPLEX_UNIT_PX, px)

fun Context.getResAnim(@AnimRes resId: Int): Animation = AnimationUtils.loadAnimation(this, resId)

fun Context.getResIntArray(@ArrayRes resId: Int): IntArray = resources.getIntArray(resId)

fun Context.getResStringArray(@ArrayRes resId: Int): Array<String> =
    resources.getStringArray(resId)

fun Context.getResTextArray(@ArrayRes resId: Int): Array<CharSequence> =
    resources.getTextArray(resId)

fun Context.getResTypedArray(@ArrayRes resId: Int): TypedArray = resources.obtainTypedArray(resId)

fun Context.getResBool(@BoolRes resId: Int): Boolean = resources.getBoolean(resId)

fun Context.getResDimen(@DimenRes resId: Int): Float = this.resources.getDimension(resId)

@Px
fun Context.getResDimenPx(@DimenRes resId: Int): Int = this.resources.getDimensionPixelSize(resId)

@Px
fun Context.getResDimenPxOffset(@DimenRes resId: Int): Int =
    this.resources.getDimensionPixelOffset(resId)

fun Context.getResInt(@IntegerRes resId: Int): Int = resources.getInteger(resId)

fun Context.getResVectorDrawable(@DrawableRes resId: Int): Drawable =
    VectorDrawableCompat.create(this.resources, resId, this.theme) as Drawable

fun Context.getResourceColor(@AttrRes resource: Int): Int {
    val typedArray = obtainStyledAttributes(intArrayOf(resource))
    val attrValue = typedArray.getColor(0, 0)
    typedArray.recycle()
    return attrValue
}

@ColorInt
fun Context.getResColor(@ColorRes resId: Int): Int = ContextCompat.getColor(this, resId)

@ColorInt
fun Fragment.getResColor(@ColorRes resId: Int): Int =
    ContextCompat.getColor(requireContext(), resId)

/**
 * Returns the color state list for this resource
 */
fun Context.getResColorStateList(resId: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, resId)

/**
 * Returns the font for this resource
 */
fun Context.getResFont(@FontRes resId: Int): Typeface = ResourcesCompat.getFont(this, resId)!!

// TINTED DRAWABLES
object ValueHolder {
    val VALUE = TypedValue()
}

// DISPLAY
val metrics = DisplayMetrics()

/**
 * Returns whether has navigation bar
 */
fun Context.hasNavigationBar(): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return id > 0 && resources.getBoolean(id)
}

fun drawableToBitmap(context: Context, resourceId: Int): Bitmap? {
    val vectorDrawable = ContextCompat.getDrawable(context, resourceId)
    val h = vectorDrawable?.intrinsicHeight
    val w = vectorDrawable?.intrinsicWidth
    w?.let { h?.let { it1 -> vectorDrawable.setBounds(0, 0, it, it1) } }
    val bm = w?.let { h?.let { it1 -> Bitmap.createBitmap(it, it1, Bitmap.Config.ARGB_8888) } }
    bm?.let { Canvas(it) }?.let { vectorDrawable.draw(it) }
    return bm
}

@Suppress("DEPRECATION")
fun Context.getVersionCode(): Int = packageManager.getPackageInfo(packageName, 0).versionCode

fun Context.getVersionName(): String = packageManager.getPackageInfo(packageName, 0).versionName


fun Context.resolveThemeAttribute(@AttrRes resId: Int): TypedValue =
    TypedValue().apply { theme.resolveAttribute(resId, this, true) }

@ColorInt
fun Context.resolveThemeColor(@AttrRes attrId: Int): Int = resolveThemeAttribute(attrId).data

inline fun <reified T : AppCompatActivity> Context.intent() = Intent(this, T::class.java)

inline fun <reified T : AppCompatActivity> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}

inline fun <reified T : AppCompatActivity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    ContextCompat.startActivity(this, intent, null)
}

inline fun <reified T : AppCompatActivity> Context.startActivity(body: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.body()
    ContextCompat.startActivity(this, intent, null)
}

inline fun <reified T : AppCompatActivity> Context.startActivity(
    @AnimRes enterResId: Int = 0,
    @AnimRes exitResId: Int = 0
) {
    val intent = Intent(this, T::class.java)
    val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : AppCompatActivity> Context.startActivity(
    @AnimRes enterResId: Int = 0, @AnimRes exitResId: Int = 0,
    body: Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java)
    intent.body()
    val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

fun Context.share(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(EXTRA_SUBJECT, subject)
    intent.putExtra(EXTRA_TEXT, text)
    return try {
        startActivity(createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

fun Context.email(
    address: Array<String> = emptyArray(),
    subject: String = "",
    text: String = ""
): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, address)
    if (subject.isNotBlank()) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotBlank()) intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false
}

fun Context.browse(url: Uri?, newTask: Boolean = false): Boolean {
    val intent = Intent(ACTION_VIEW)
    intent.data = url
    if (newTask) intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    return try {
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

fun Context.makeCall(tel: String): Boolean {
    return try {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel")))
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Context.sendSMS(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(ACTION_VIEW, Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Context.directPlayStore() =
    browse(Uri.parse("http://play.google.com/store/apps/details?id=$packageName"))

fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}