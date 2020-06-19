package com.earlyBuddy.earlybuddy_android.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel : BaseViewModel(EarlyBuddyApplication.globalApplication){

    private var _schedule = MutableLiveData<ArrayList<Schedule>>()
    val schedule : LiveData<ArrayList<Schedule>> get() = _schedule

    fun getSchedule(year: String, month: String) : ArrayList<Schedule>{
        val cal = Calendar.getInstance()
        cal.set(year.toInt(), month.toInt(), 1)

        val scheduleByDate = ArrayList<Schedule>()

        if(year == "2020" && month == "06") {
            scheduleByDate.add(
                Schedule(
                    0,
                    "제주도 가기",
                    "2020.06.20 15:00",
                    "김포공항"
                )
            )

            scheduleByDate.add(
                Schedule(
                    0,
                    "집으로 가기",
                    "2020.06.23 15:00",
                    "제주공항"
                )
            )
        }

        schedule.value?.let{
            for(i in it.indices){
                scheduleByDate.add(it[i])
            }
        }

        return scheduleByDate
    }

}