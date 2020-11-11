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
    val nextArriveMinuteDifference = MutableLiveData<String>()
    var trafficType: String = ""
    var untilDepartCode: String = ""
    val lastsSentence = MutableLiveData<String>()
    val frontSentence = MutableLiveData<String>()
    var trafficNumber = ""
    val lastCount = MutableLiveData<Int>()
    var tints: String = ""
    val remainingMinute = MutableLiveData<Int>()
    val timer = Timer()
    val nextArriveStop = MutableLiveData<Unit>()
    val nextInVisible = MutableLiveData<Unit>()

    fun getData(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse

        untilDepartCode = tempHomeResponse.data!!.untilDepartCode.toString()
        lastCount.value = tempHomeResponse.data.untilDepartCode
        when (untilDepartCode) {
            "3" -> {
                when (tempHomeResponse.data.firstTrans.trafficType) {
                    1 -> {
                        frontSentence.value = "지금 오는 지하철이 마지막!"
                    }
                    2 -> {
                        frontSentence.value = "지금 오는 버스가 마지막!"
                    }
                }
                lastsSentence.value = "이번에 놓치면 지각이에요!"
            }
            "2" -> {
                when (tempHomeResponse.data.firstTrans.trafficType) {
                    1 -> {
                        frontSentence.value = "타야할 지하철이 오고있어요!"
                    }
                    2 -> {
                        frontSentence.value = "타야할 버스가 오고있어요!"
                    }
                }
                lastsSentence.value = "이젠 나갈 준비를 해주세요!"
            }
            "1" -> {
                when (tempHomeResponse.data.firstTrans.trafficType) {
                    1 -> {
                        frontSentence.value = "타야할 지하철이 오고있어요!"
                    }
                    2 -> {
                        frontSentence.value = "타야할 버스가 오고있어요!"
                    }
                }
                lastsSentence.value = "오늘은 여유롭게 도착해볼까요?"

            }
            else -> {
                when (tempHomeResponse.data.firstTrans.trafficType) {
                    1 -> {
                        frontSentence.value = "운행중인 지하철이 없습니다!"
                    }
                    2 -> {
                        frontSentence.value = "운행중인 버스가 없습니다!"
                    }
                }
                lastsSentence.value = "새벽 약속은 불가합니다!"

            }
        }
        divideTraffic(tempHomeResponse)
        getTimeDifference(tempHomeResponse)
    }

    private fun divideTraffic(tempHomeResponse: HomeResponse) {
        when (tempHomeResponse.data!!.firstTrans.trafficType) {
            1 -> {
                trafficType = "지하철 도착까지"
                //("지하철일때 번호 표시")
                trafficNumber = tempHomeResponse.data.firstTrans.subwayLane.toString() + "호선"

                // ("지하철 노선에 따른 박스 색깔 변화")

                tints = TransportMap.subwayMap[tempHomeResponse.data.firstTrans.subwayLane]!![0]
            }
            2 -> {
                trafficType = "버스 도착까지"
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

        if(arriveTime != null && nextTransArriveTime != null) {
            when (arriveTime) {
                "곧 도착" -> {
                    remainingMinute.value = 0
                }
                "운행종료" -> {
                    remainingMinute.value = -3
                }
                "출발대기" -> {
                    remainingMinute.value = -5
                }
                else -> {
                    val firstArriveTime = sdf.parse(arriveTime)
                    getFirstArriveTime(firstArriveTime, nowDate)
                }
            }


            when (nextTransArriveTime) {
//            "곧 도착" -> {
//                nextTransArriveTime = sdf.format(Date())
//                getNextArriveTime(sdf.parse(nextTransArriveTime), nowDate)
//            }
                "마지막" -> {
                    nextInVisible.value = Unit
                }
                "운행종료" -> {
                    nextArriveStop.value = Unit
                }
                "출발대기" -> {
                    nextInVisible.value = Unit
                }
                else -> {
                    val nextArriveTime = sdf.parse(nextTransArriveTime)
                    getNextArriveTime(nextArriveTime, nowDate)
                }
            }
        }
    }

    private fun getFirstArriveTime(firstArriveTime: Date, nowDate: Date) {
        arriveMinuteDifference.value = getGapTime(firstArriveTime, nowDate)
        var remainingMinuteValue = arriveMinuteDifference.value!!
        remainingMinute.value = (remainingMinuteValue)
    }

    private fun getNextArriveTime(nextArriveTime: Date, nowDate: Date) {
        val timeDifference = getGapTime(nextArriveTime,nowDate)
        if(timeDifference==0){
            nextArriveMinuteDifference.value = "잠시 후"
        }else{
        nextArriveMinuteDifference.value =
            "다음 배차까지 $timeDifference 분"

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