package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import androidx.databinding.BindingAdapter

@BindingAdapter("calendar_height")
fun setHeight(view: View, lines: Int) {
    view.parent
    val layoutParams = view.layoutParams
    val wm = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    val height = size.y// - 480

    layoutParams.height = ((height / lines) / 1.35).toInt()
    view.layoutParams = layoutParams
}