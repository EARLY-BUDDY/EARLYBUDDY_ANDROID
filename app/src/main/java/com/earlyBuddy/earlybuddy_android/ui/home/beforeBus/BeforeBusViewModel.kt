package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class BeforeBusViewModel() : BaseViewModel() {

    val homeResponse = MutableLiveData<HomeResponse>()
    val startTime = MutableLiveData<String>()
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

                //TODO("지하철일때 번호 표시")
                trafficNumber = tempHomeResponse.data.firstTrans.subwayLane.toString() + "호선"


                // TODO("지하철 노선에 따른 박스 색깔 변화")

                tints = TransportMap.subwayMap[tempHomeResponse.data.firstTrans.subwayLane]!![0]
            }
            2 -> {
                trafficType = "버스는"

                //TODO("버스 일때 번호 표시")
                trafficNumber = tempHomeResponse.data.firstTrans.busNo.toString()

                // TODO("버스 노선에 따른 박스 색깔 변화")
                tints = TransportMap.busMap[tempHomeResponse.data.firstTrans.busType]!!

            }
        }

    }

    private fun getTimeDifference(tempHomeResponse: HomeResponse) {
        val scheduleStartTime =
            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var arriveTime = tempHomeResponse.data.arriveTime

        val nextTransArriveTime = tempHomeResponse.data.nextTransArriveTime

        if (arriveTime == "곧 도착") {
            arriveTime = sdf.format(Date())
        }

        val nowDate = Date()
        val promiseStartTime = sdf.parse(scheduleStartTime)
        val firstArriveTime = sdf.parse(arriveTime)
        val nextArriveTime = sdf.parse(nextTransArriveTime)

        var tempMin = ""
        var tempHour = ""

        tempMin = if (promiseStartTime.minutes.toString().length == 1) {
            "0" + promiseStartTime.minutes
        } else {
            promiseStartTime.minutes.toString()
        }

        tempHour = if (promiseStartTime.hours.toString().length == 1) {
            "0" + promiseStartTime.hours
        } else {
            promiseStartTime.hours.toString()
        }

        if (promiseStartTime.hours >= 12) {
            startTime.value = "오후 $tempHour:$tempMin"
        } else {
            startTime.value = "오전 $tempHour:$tempMin"
        }

        val arriveGap = firstArriveTime.time - nowDate.time
        val nextArriveGap = nextArriveTime.time - nowDate.time

        val arriveDiffDay = arriveGap / 1000 / 60 / 60 / 24
        val arriveDiffHour = arriveGap / 1000 / 60 / 60 - (arriveDiffDay * 24)
        val arriveDiffMinute =
            arriveGap / 1000 / 60 - (arriveDiffDay * 24 * 60) - (arriveDiffHour * 60)

        arriveMinuteDifference.value = arriveDiffMinute.toInt() + arriveDiffHour.toInt() * 60

        val nextArriveDiffDay = nextArriveGap / 1000 / 60 / 60 / 24
        val nextArriveDiffHour = nextArriveGap / 1000 / 60 / 60 - (nextArriveDiffDay * 24)
        val nextArriveDiffMinute =
            nextArriveGap / 1000 / 60 - (nextArriveDiffDay * 24 * 60) - (nextArriveDiffHour * 60)

        nextArriveMinuteDifference.value =
            nextArriveDiffMinute.toInt() + nextArriveDiffHour.toInt() * 60

        Log.e("다음 도착까지", arriveMinuteDifference.value.toString())

        var remainingMinuteValue = arriveMinuteDifference.value!!
        remainingMinute.value = (remainingMinuteValue)

//        // 분 줄여가기 (1분마다/60000 밀리세크)
//        timer.scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                remainingMinuteValue--
//                remainingMinute.postValue(remainingMinuteValue)
//                Log.e("ttt", remainingMinute.value.toString())
//            }
//        }, 6000, 6000)
    }

}