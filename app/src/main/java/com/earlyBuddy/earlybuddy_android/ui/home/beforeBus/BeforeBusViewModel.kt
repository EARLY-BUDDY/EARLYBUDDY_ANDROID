package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class BeforeBusViewModel() : BaseViewModel() {

    val homeResponse = MutableLiveData<HomeResponse>()
    val arriveMinuteDifference = MutableLiveData<Int>()
    val nextArriveMinuteDifference = MutableLiveData<Int>()
    var trafficType: String = ""
    var lastTransCount: String = ""
    val lastsSentence = MutableLiveData<String>()
    var trafficNumber = ""
    val lastCount = MutableLiveData<Int>()
    var tints: String = ""
    val remainingMinute = MutableLiveData<Int>()
    val timer = Timer()
    val nextArriveStop = MutableLiveData<Unit>()

    fun getData(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        lastTransCount = tempHomeResponse.data!!.lastTransCount.toString()
        lastCount.value = tempHomeResponse.data.lastTransCount
        if (lastTransCount == "2") {
            lastsSentence.value = "이제 나갈 준비를 해주세요!"
        } else {
            lastsSentence.value = "이번에 놓치면 지각이에요!"
        }
        divideTraffic(tempHomeResponse)
        getTimeDifference(tempHomeResponse)
    }

    private fun divideTraffic(tempHomeResponse: HomeResponse) {
        when (tempHomeResponse.data!!.firstTrans.trafficType) {
            1 -> {
                trafficType = "지하철은"

                //("지하철일때 번호 표시")
                trafficNumber = tempHomeResponse.data.firstTrans.subwayLane.toString() + "호선"

                // ("지하철 노선에 따른 박스 색깔 변화")

                tints = TransportMap.subwayMap[tempHomeResponse.data.firstTrans.subwayLane]!![0]
            }
            2 -> {
                trafficType = "버스는"

                //("버스 일때 번호 표시")
                trafficNumber = tempHomeResponse.data.firstTrans.busNo.toString()

                // ("버스 노선에 따른 박스 색깔 변화")
                tints = TransportMap.busMap[tempHomeResponse.data.firstTrans.busType]!!

            }
        }

    }

    private fun getTimeDifference(tempHomeResponse: HomeResponse) {

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var arriveTime = tempHomeResponse.data!!.arriveTime

        var nextTransArriveTime = tempHomeResponse.data.nextTransArriveTime

        val nowDate = Date()

        when (arriveTime) {
            "곧 도착" -> {
                remainingMinute.value = 0
            }
            "운행종료" -> {
                remainingMinute.value = -3
            }
            else -> {
                val firstArriveTime = sdf.parse(arriveTime)
                getFirstArriveTime(firstArriveTime, nowDate)
            }
        }

        when (nextTransArriveTime) {
            "곧 도착" -> {
                nextTransArriveTime = sdf.format(Date())
                getNextArriveTime(sdf.parse(nextTransArriveTime), nowDate)
            }
            "운행종료" -> {
                nextArriveStop.value = Unit
            }
            else -> {
                val nextArriveTime = sdf.parse(nextTransArriveTime)
                getNextArriveTime(nextArriveTime, nowDate)
            }
        }
    }

    private fun getFirstArriveTime(firstArriveTime: Date, nowDate: Date) {
        arriveMinuteDifference.value = getGapTime(firstArriveTime, nowDate)
        var remainingMinuteValue = arriveMinuteDifference.value!!
        remainingMinute.value = (remainingMinuteValue)
    }

    private fun getNextArriveTime(nextArriveTime: Date, nowDate: Date) {
        nextArriveMinuteDifference.value =
            getGapTime(nextArriveTime, nowDate)
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