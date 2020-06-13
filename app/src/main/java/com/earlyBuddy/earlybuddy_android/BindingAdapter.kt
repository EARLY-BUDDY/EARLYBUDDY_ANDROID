package com.earlyBuddy.earlybuddy_android

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("trafficType", "endName")
fun walkEndText(textView: TextView, nextTrafficType: Int, endName: String?) {
    if (nextTrafficType == -1) {
        return
    }
    when (nextTrafficType) {
        1 -> textView.text =
            String.format("%s역까지 걷기", endName)
        2 -> textView.text =
            String.format("%s까지 걷기", endName)
    }
}

@BindingAdapter("trafficType", "startName")
fun walkStartText(textView: TextView, previousTrafficType: Int, startName: String?) {
    if (previousTrafficType == -1) {
        return
    }
    when (previousTrafficType) {
        1 -> textView.text =
            String.format("%%d번 출구로 나오기", startName)
        2 -> textView.text =
            String.format("%s 하차", startName)
    }
}

@BindingAdapter("changeTint")
fun ImageView.changeTint(tints: String) {
    backgroundTintList = ColorStateList.valueOf(Color.parseColor(tints))
}


@BindingAdapter("changeTextBack")
fun ConstraintLayout.changeTint(tints: String) {
    backgroundTintList = ColorStateList.valueOf(Color.parseColor(tints))
}
@BindingAdapter("changeImg", "imgTint")
fun ImageView.changeImg(image: Drawable, tints: String) {
    background = image
    backgroundTintList = ColorStateList.valueOf(Color.parseColor(tints))
}