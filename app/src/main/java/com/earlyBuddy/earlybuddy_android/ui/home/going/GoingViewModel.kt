package com.earlyBuddy.earlybuddy_android.ui.home.going

import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat
import java.util.*

class GoingViewModel : BaseViewModel() {
    val homeResponse = MutableLiveData<HomeResponse>()
    val remainTime = MutableLiveData<String>()

    fun getDate(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        val gapTime = getGapTime(
            sdf.parse(tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime),
            Date()
        )
        val gapHour = gapTime / 60
        val gapMinute = gapTime % 60
        if (gapHour == 0) {
            remainTime.value = gapMinute.toString() + "분"
        } else {
            remainTime.value = gapHour.toString() + "시간 " + gapMinute.toString() + "분"
        }
    }

    private fun getGapTime(arriveTime: Date, nowDate: Date): Int {
        val arriveGap = arriveTime.time - nowDate.time
        val arriveDiffDay = arriveGap / 1000 / 60 / 60 / 24
        val arriveDiffHour = arriveGap / 1000 / 60 / 60 - (arriveDiffDay * 24)
        val arriveDiffMinute =
            arriveGap / 1000 / 60 - (arriveDiffDay * 24 * 60) - (arriveDiffHour * 60)

        return arriveDiffMinute.toInt() + arriveDiffHour.toInt() * 60
    }
}