package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.earlyBuddy.earlybuddy_android.R

@BindingAdapter("scheduleStartTime")
fun TextView.setScheduleStartTime(time: String?) {
        time?.run{
                var temp = substring(0, 4) + "." + substring(5, 7) + "." + substring(8, 10)

                var hour = substring(11,13).toInt()
                if(hour < 12) temp += " 오전 $hour"
                else if (hour == 12) temp += " 오후 $hour"
                else temp += " 오후 ${hour - 12}"

                temp += substring(13, 16)
                text = temp
        }
}

@BindingAdapter("noticeMin")
fun TextView.setNoticeMin(noticeMin: Int) {
    text = "배차 ${noticeMin}분 전"
}

@BindingAdapter("noticeRange")
fun TextView.setNoticeRange(noticeRange: Int) {
        var temp = ""
        if(noticeRange < 60){
                temp = "${noticeRange}분 전부터"
        } else {
                temp = "${noticeRange/60}시간 전부터"
        }
        text = temp
}

@BindingAdapter("departTime")
fun TextView.setDepartTime(time: String?) {
        time?.run{
                var temp = "약 "

                var hour = substring(11,13).toInt()
                if(hour < 12) temp += " 오전 ${hour}시 "
                else if (hour == 12) temp += " 오후 ${hour}시 "
                else temp += " 오후 ${hour - 12}시 "

                var min = substring(14, 16).toInt()
                if(min == 0) temp += "정각"
                else temp += min.toString() + "분"

                text = temp
        }
}

@BindingAdapter("scheduleDetailWeekDayBg")
fun ConstraintLayout.setScheduleDetailWeekDayBg(isSelected: Boolean) {
        if(isSelected){
                background = resources.getDrawable(R.drawable.circle_ff6e6e_fill)
        } else {
                background = resources.getDrawable(R.drawable.circle_c3c3c3)
        }
}

@BindingAdapter("scheduleDetailWeekDayText")
fun TextView.setScheduleDetailWeekDayTextColor(isSelected: Boolean) {
        if(isSelected){
                setTextColor(Color.parseColor("#ffffff"))
        } else {
                setTextColor(Color.parseColor("#c3c3c3"))
        }
}
