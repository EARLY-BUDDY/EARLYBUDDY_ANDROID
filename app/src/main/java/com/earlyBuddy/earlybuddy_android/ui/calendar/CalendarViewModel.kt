package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import com.earlyBuddy.earlybuddy_android.data.repository.CalendarRepository
import com.earlyBuddy.earlybuddy_android.util.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel(
    private val calendarRepository : CalendarRepository
    ) : BaseViewModel(){

    val dummySchedule = arrayListOf(
        Schedule(0, "제주도 가기", "2020.08.20 15:00", "김포공항"),
        Schedule(1, "제주도 취소", "2020.08.20 15:00", "인 마이 하우스"),
        Schedule(2, "집으로 가기", "2020.08.23 15:00", "제주공항"),
        Schedule(3, "얼리버디 여행", "2020.09.23 15:00", "가평 빠지"),
        Schedule(4, "캘린더 완성", "2020.08.09 11:00", "충무로 투썸")
    )

    val scheduleByMonth = ArrayList<Schedule>()

    private var _schedule = MutableLiveData<ArrayList<Schedule>>()
    val schedule : LiveData<ArrayList<Schedule>> get() = _schedule


    fun getSchedule(year: String, month: String){

        Log.e("month", month)

        addDisposable(calendarRepository.getCalendarSchedules(year, month)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e("get Schedule error", it.message)
            }
            .subscribe({
                it.run{
                    if(this.status == 200){
                        _schedule.value = this.data.schedules
//                        Log.e("schedules", this.data.schedules.toString())
//                        this.data.schedules.let{
//                            for(i in it.indices){
//                                scheduleByMonth.add(it[i])
//                            }
////                        }
//
//                        Log.e("scheduleByMonth", scheduleByMonth.toString())
                    }
                }

            }) {
                Log.e("get Schedule error:", it.message)
            })
    }

    fun getScheduleByMonth(year : String, month: String) : ArrayList<Schedule>{
        schedule.value?.run{
            scheduleByMonth.clear()
            scheduleByMonth.addAll(this)
        }
        return scheduleByMonth
    }

    fun getScheduleByDay(year: String, month : String, day : String) : ArrayList<Schedule>{

        val scheduleByDay = arrayListOf<Schedule>()

        if(schedule.value != null){
            for(schedule in schedule.value!!){
                val seperated = schedule.scheduleStartTime.split('-').toTypedArray()
                val y = seperated[0]
                val m = seperated[1]
                val d = seperated[2].split(' ').toTypedArray()[0]

                if(y == year && m == month && d.toInt() == day.toInt()){
                    scheduleByDay.add(schedule)
                }
            }
        }

        return scheduleByDay
    }

}