package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.util.Log
import android.view.View
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

    private val _isProgress = MutableLiveData<Int>()
    val isProgress: LiveData<Int> get() = _isProgress

    val scheduleByMonth = ArrayList<Schedule>()

    private var _schedule = MutableLiveData<ArrayList<Schedule>>()
    val schedule : LiveData<ArrayList<Schedule>> get() = _schedule

    fun getSchedule(year: String, month: String){

        addDisposable(calendarRepository.getCalendarSchedules(year, month)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showProgress()
            }
            .doOnTerminate {
                hideProgress()
            }
            .doOnError {
                Log.e("get Schedule error", it.message)
//                lottieVisible.value = false
            }
            .subscribe({
                it.run{
                    if(this.status == 200){
                        _schedule.value = this.data.schedules
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

    private fun showProgress() {
        _isProgress.value = View.VISIBLE
    }

    private fun hideProgress() {
        _isProgress.value = View.INVISIBLE
    }


}