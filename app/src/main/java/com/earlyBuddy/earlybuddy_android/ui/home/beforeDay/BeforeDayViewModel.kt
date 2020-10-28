package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class BeforeDayViewModel() : BaseViewModel() {

    val homeResponse = MutableLiveData<HomeResponse>()
    val timeDifference = MutableLiveData<Int>()
    val timeDivision = MutableLiveData<String>()
    val beforeDayText = MutableLiveData<String>()

    fun getData(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        beforeDayText.value = tempHomeResponse.data!!.userName+"님!\n잘 지내고 있나요?"
        getTimeDifference(tempHomeResponse)
    }

    private fun getTimeDifference(tempHomeResponse: HomeResponse) {
        val scheduleStartTime =
            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        val date = Date()

        val promise = sdf.parse(scheduleStartTime)

        Log.e("현재시간 : ", sdf.format(date).toString())
        Log.e("약속시간 : ", sdf.format(promise).toString())

        val gap = promise.time - date.time

        timeDifference.value = (gap / 1000 / 60 / 60 / 24).toInt()


        val diffDay = (gap / 1000 / 60 / 60 / 24).toInt()
        val diffHour = (gap / 1000 / 60 / 60 - (diffDay * 24)).toInt()
        val diffMinute = (gap / 1000 / 60 - (diffDay * 24 * 60) - (diffHour * 60)).toInt()
        val diffSecond =
            ((gap / 1000 - (diffDay * 24 * 60 * 60) - (diffHour * 60 * 60) - (diffMinute * 60))).toInt()


        if (diffDay > 0) {
            timeDifference.value = promise.day - date.day
            timeDivision.value = "일 전"
        } else {
            timeDifference.value = diffHour
            timeDivision.value = "시간 전"
        }

        Log.e("일 차이 : ", diffDay.toString())
        Log.e("시간 차이 : ", diffHour.toString())
        Log.e("분 차이 : ", diffMinute.toString())
        Log.e("초 차이 : ", diffSecond.toString())

        Log.e("일 차이 : ", timeDifference.value.toString())


    }
}