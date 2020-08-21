package com.earlyBuddy.earlybuddy_android

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import org.w3c.dom.Text

@BindingAdapter("trafficType", "endName", "fastInExitNo")
fun walkEndText(textView: TextView, nextTrafficType: Int, endName: String?, fastInExitNo: Int) {
    if (nextTrafficType == -1) {
        return
    }
    when (nextTrafficType) {
        1 -> {
            if (fastInExitNo == 0) {
                textView.text = String.format("%s역까지 걷기", endName)
            } else {
                textView.text =
                    String.format("%s역 %d번 출구까지 걷기", endName, fastInExitNo)
            }
        }
        2 -> textView.text =
            String.format("%s 정류장까지 걷기", endName)
    }
}

@BindingAdapter("trafficType", "startName", "fastOutExitNo")
fun walkStartText(
    textView: TextView,
    previousTrafficType: Int,
    startName: String?,
    fastOutExitNo: Int
) {
    if (previousTrafficType == -1) {
        return
    }
    when (previousTrafficType) {
        1 -> textView.text =
            String.format("%s역 %d번 출구로 나오기", startName, fastOutExitNo)
        2 -> textView.text =
            String.format("%s 정류장 하차", startName)
    }
}

@BindingAdapter("changeTint")
fun View.changeTint(tints: String) {
    if (this is ImageView || this is ConstraintLayout) {
        backgroundTintList = ColorStateList.valueOf(Color.parseColor(tints))
    }
}

@BindingAdapter("changeImg", "imgTint")
fun ImageView.changeimg(image: Drawable, tints: String) {
    background = image
    backgroundTintList = ColorStateList.valueOf(Color.parseColor(tints))
}

@BindingAdapter("trafficType", "name")
fun namingYuk(view: TextView, trafficType: Int, name: String?) {
    if (trafficType == 1) {
        view.text = "${name}역"
    } else {
        view.text = "$name 정류장"
    }
}

@BindingAdapter("trafficType", "fastDoor")
fun namingFastDoor(view: TextView, trafficType: Int, fastDoor: String?) {

    fastDoor?.let {
        val str = fastDoor.split("-")
        if (trafficType == 1) {
            view.text = "빠른 환승 : ${str[0]} - ${str[1]}"
        } else {
            view.text = "방향을 확인하고 타세요"
        }
    }
}

@BindingAdapter("remainingMinuteSetHide", "changeText")
fun hideText(view: TextView, remainingMinuteSetHide: Int, changeText: Boolean) {
    if (remainingMinuteSetHide <= 3) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
        if (changeText) {
            view.text = remainingMinuteSetHide.toString()
        }
    }
}

@BindingAdapter("remainingMinuteSetVisible")
fun visibleText(view: TextView, remainingMinuteSetVisible: Int) {
    if (remainingMinuteSetVisible <= 3) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("placeResultRoadAddress", "placeResultAddressName")
fun TextView.placeResultAddress(placeResultRoadAddress: String, placeResultAddressName: String) {
    text = if (placeResultRoadAddress.isEmpty()) {
        placeResultAddressName
    } else {
        placeResultRoadAddress
    }
}

@BindingAdapter("setMethodColor", "setMethodColorType")
fun View.setMethodColor(color: String?, type: Int) {
    if (this is ImageView) {
        if (type == 3) visibility = View.INVISIBLE
        else {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
        }
    }
    if (this is TextView) {
        if (type != 3) {
            setTextColor(ColorStateList.valueOf(Color.parseColor(color)))
        }
    }
}

@BindingAdapter("setTotalTime")
fun TextView.setTotalTime(time: Int?) {
    text = if (time!! < 60) "${time}분"
    else "${time / 60}시간 ${time % 60}분"
}

@BindingAdapter("setPathType")
fun TextView.setPathType(type: Int?) {
    text = if (type==1) "지하철"
    else if(type==2) "버스"
    else "지하철 + 버스"
}

@BindingAdapter("setTransitCount")
fun TextView.setTransitCount(cnt: Int?) {
    text = "환승 ${cnt}회"
}

@BindingAdapter("setWalkTime")
fun TextView.setWalkTime(time: Int?) {
    text = "도보 ${time}분"
}

@BindingAdapter("setPay")
fun TextView.setPay(pay: String?) {
    val size = pay!!.length
    text = "${pay.substring(0, size - 3)},${pay.substring(size - 3)}원"
}