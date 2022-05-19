package com.loadmovietestapp.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.Dimension
import androidx.annotation.Px
import com.loadmovietestapp.app.TestApplication
import timber.log.Timber
import kotlin.math.roundToInt

@Px
fun dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int {
    val displayMetrics = TestApplication.instance.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
        .toInt()
}

fun pxToDp(px: Int): Int {
    val displayMetrics: DisplayMetrics = TestApplication.instance.resources.displayMetrics
    return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}


fun checkConnectedToANetwork(context: Context): Boolean {
    //
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //
    val activeNetwork = cm.activeNetworkInfo
    //
    val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
    //
    if (!isConnected) {
        Timber.e("No internet connection")
        return false
    }
    //
    return true
}