package com.earlyBuddy.earlybuddy_android.ui.home.going

import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat

class GoingViewModel : BaseViewModel() {
    val homeResponse = MutableLiveData<HomeResponse>()
    val startTime = MutableLiveData<String>()

    fun getDate(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        val scheduleStartTime =
            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


//        val date = Date()
        val date = sdf.parse("2020-07-23 01:15:00")

        val promise = sdf.parse(scheduleStartTime)

        if (promise.hours >= 12) {
            startTime.value = "오후 ${String.format("%02d:%02d", promise.minutes, promise.hours)}"
        } else {
            startTime.value = "오전 ${String.format("%02d:%02d", promise.minutes, promise.hours)}"
        }
    }
}