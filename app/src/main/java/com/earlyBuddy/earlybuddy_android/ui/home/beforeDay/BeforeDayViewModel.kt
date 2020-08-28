package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat

@Suppress("DEPRECATION")
class BeforeDayViewModel() : BaseViewModel() {

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


//        val date = Date()
        val date = sdf.parse("2020-07-23 01:15:00")

        val promise = sdf.parse(scheduleStartTime)
        var tempMin = ""
        var tempHour = ""

        tempHour = String.format("%02d", promise.hours)
        tempMin = String.format("%02d", promise.minutes)
//        var promiseMinute = ""
//
//
//        if (promise.minutes < 10) {
//            promiseMinute = "0${promise.minutes}"
//        } else {
//            promiseMinute = promise.minutes.toString()
//        }

        if (promise.hours >= 12) {
            startTime.value = "오후 $tempHour:$tempMin"
        } else {
            startTime.value = "오전 $tempHour:$tempMin"
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
            if (promise.day - date.day == 1) {
                // 내일이라고 표시 근데 여기서 2일 차이나는거랑 1일 차이나는 걸 구분해줘야한다.

                date.minutes = 0
                date.hours = 0
                date.seconds = 0

                val tempGap = promise.time - date.time
                val tempDiffDay = (tempGap / 1000 / 60 / 60 / 24).toInt()
                if(tempDiffDay==1){
                    // 내일이라고 표시를 해줘야한다.
                    timeDifference.value = -1
                }else{
                    timeDifference.value = tempDiffDay
                }


            } else {
                timeDifference.value = diffDay
            }
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