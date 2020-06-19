package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat
import java.util.*

class BeforeDayViewModel(application: EarlyBuddyApplication): BaseViewModel(application) {

    val homeResponse = MutableLiveData<HomeResponse>()
    val timeDifference = MutableLiveData<Int>()
    val startTime = MutableLiveData<String>()
    val moreThanDay = MutableLiveData<Boolean>()

    fun getData(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        getTimeDifference(tempHomeResponse)

    }

    private fun getTimeDifference(tempHomeResponse: HomeResponse) {
        val scheduleStartTime =
            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        val date = Date()
        val promise = sdf.parse(scheduleStartTime)

        if (promise.hours >= 12) {
            startTime.value = "오후 " + promise.hours + ":" + promise.minutes
        } else {
            startTime.value = "오전 " + promise.hours + ":" + promise.minutes
        }

        Log.e("현재시간 : ", sdf.format(date).toString())
        Log.e("약속시간 : ", sdf.format(promise).toString())
        val gap = promise.time - date.time

        timeDifference.value = (gap / 1000 / 60 / 60 / 24).toInt()


        val diffDay = (gap / 1000 / 60 / 60 / 24).toInt()
        val diffHour = (gap / 1000 / 60 / 60 - (diffDay * 24)).toInt()
        val diffMinute = (gap / 1000 / 60 - (diffDay * 24 * 60) - (diffHour * 60)).toInt()
        val diffSecond =
            ((gap / 1000 - (diffDay * 24 * 60 * 60) - (diffHour * 60 * 60) - (diffMinute * 60))).toInt()

        moreThanDay.value = diffDay > 0

        if (diffDay > 0) {
            timeDifference.value = diffDay
        } else {
            timeDifference.value = diffHour
        }

        Log.e("일 차이 : ", diffDay.toString())
        Log.e("시간 차이 : ", diffHour.toString())
        Log.e("분 차이 : ", diffMinute.toString())
        Log.e("초 차이 : ", diffSecond.toString())

        Log.e("일 차이 : ", timeDifference.value.toString())


    }
}