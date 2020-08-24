package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule

@BindingAdapter("calendar_day_height")
fun setHeight(view: View, lines: Int) {
    view.parent
    val layoutParams = view.layoutParams

    val dpHeight = 300
    val metrics = view.context.resources.displayMetrics
    val height = dpHeight * (metrics.densityDpi / 160f)

    Log.e("height", height.toString())
    Log.e("lines", lines.toString())

    layoutParams.height = (height / lines).toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("calendar_day_schedule_visibility")
fun dayScheduleVisibility(view: ImageView, schedules: ArrayList<Schedule>?){
    if(schedules?.size == 0){
        view.visibility = INVISIBLE
    }else{
        view.visibility = VISIBLE
    }
}


@BindingAdapter("calendar_today_background","calendar_click_day_background")
fun setCalendarClickDayBackground(view: ConstraintLayout, isToday: Boolean, isClick: Boolean){
    if(isToday && isClick || isClick){
        view.setBackgroundResource(R.drawable.circle_ff6e6e_fill)
    }else if(isToday){
        view.setBackgroundResource(R.drawable.circle_c3c3c3_fill)
    }else{
        view.setBackgroundColor(view.context.getColor(R.color.white))
    }
}

@BindingAdapter("calendar_today_text_color")
fun setCalendarTodayTextColor(view: TextView, bool: Boolean){
    if(bool){
        view.setTextColor(view.context.getColor(R.color.white))
    }
}


@BindingAdapter("calendar_day_text_color")
fun setCalendarDayTextColor(view: TextView, isCurrentMonth: Boolean){
    if(isCurrentMonth){
        view.setTextColor(view.context.getColor(R.color.black))
    }else{
        view.setTextColor(view.context.getColor(R.color.light_gray))
    }
}

@BindingAdapter("calendar_click_day_text")
fun setCalendarClickDayText(view: TextView, bool: Boolean){
    if(bool){
        view.setTextColor(view.context.getColor(R.color.white))
    }
}
