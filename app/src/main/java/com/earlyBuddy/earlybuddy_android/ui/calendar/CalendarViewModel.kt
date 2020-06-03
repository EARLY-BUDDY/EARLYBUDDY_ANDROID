package com.earlyBuddy.earlybuddy_android.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel : BaseViewModel(){

    private var _schedule = MutableLiveData<ArrayList<Schedule>>()
    val schedule : LiveData<ArrayList<Schedule>> get() = _schedule

    fun getSchedule(year: String, month: String) : ArrayList<Schedule>{
        val cal = Calendar.getInstance()
        cal.set(year.toInt(), month.toInt(), 1)

        val scheduleByDate = ArrayList<Schedule>()
        schedule.value?.let{
            for(i in it.indices){
                scheduleByDate.add(it[i])
            }
        }

        return scheduleByDate
    }

}