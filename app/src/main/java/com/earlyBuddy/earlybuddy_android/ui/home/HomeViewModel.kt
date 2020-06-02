package com.earlyBuddy.earlybuddy_android.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.FirstTrans
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeSchedule
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleSummaryData
import com.earlyBuddy.earlybuddy_android.ui.home.beforeBus.BeforeBusFragment
import com.earlyBuddy.earlybuddy_android.ui.home.beforeDay.BeforeDayFragment
import com.earlyBuddy.earlybuddy_android.ui.home.noSchedule.NoScheduleFragment

class HomeViewModel:BaseViewModel(){

    val goBeforBusFragment = MutableLiveData<Fragment>()
    val goBeforeDayFragment = MutableLiveData<Fragment>()
    val goNoScheduleFragment = MutableLiveData<Fragment>()
    val homeResponse = MutableLiveData<HomeResponse>()

    init {
        getData()
    }

    private fun getData() {
        val tempHomeResponse: HomeResponse =
            HomeResponse(
                status = 200,
                message = "홈 화면에 보여줄 일정이 없습니다",
                data = HomeSchedule(
                    scheduleCheck = 2,
                    ready = true,
                    scheduleSummaryData = ScheduleSummaryData(
                        scheduleIdx = 3,
                        scheduleName = "소풍가기",
                        scheduleStartTime = "2020-05-24 01:20:00",
                        endAddress = "서울 송파구 양재대로 1218"
                    ),
                    lastTransCount = 1,
                    arriveTime = "2020-05-24 00:20:00",
                    firstTrans = FirstTrans(
                        detailIdx = 2,
                        trafficType = 1, // 1 -> 지하철 2 -> 버스
                        subwayLane = 2,
                        busNo = null,
                        busType = null,
                        detailStartAddress = "홍대입구역"
                    ),
                    nextTransArriveTime = "2020-05-24 00:50:00"
                )
            )

        homeResponse.value = tempHomeResponse


        when (tempHomeResponse.data!!.scheduleCheck) {
            1 -> {
                goNoScheduleFragment.value = NoScheduleFragment()
                return
            }
            2 -> {
                goBeforeDayFragment.value = BeforeDayFragment()
                return
            }
            else -> {
                goBeforBusFragment.value = BeforeBusFragment()
                return
            }
        }

    }

//    private fun getTimeDifference(tempHomeResponse: HomeResponse) {
//        val scheduleStartTime =
//            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime
//
//        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
////
////
//        val nowDate = Date()
//        val promiseDate = sdf.parse(scheduleStartTime)
//        Log.e("현재시간 : ", sdf.format(nowDate).toString())
//        Log.e("약속시간 : ", sdf.format(promiseDate).toString())
//
//        val gap = promiseDate.time - nowDate.time
////
//        val diffDay = (gap / 1000 / 60 / 60 / 24).toInt()
//        val diffHour = (gap / 1000 / 60 / 60 - (diffDay * 24)).toInt()
//        val diffMinute = (gap / 1000 / 60 - (diffDay * 24 * 60) - (diffHour * 60)).toInt()
//        val diffSecond =
//            ((gap / 1000 - (diffDay * 24 * 60 * 60) - (diffHour * 60 * 60) - (diffMinute * 60))).toInt()
//
//        Log.e("일 차이 : ", diffDay.toString())
//        Log.e("시간 차이 : ", diffHour.toString())
//        Log.e("분 차이 : ", diffMinute.toString())
//        Log.e("초 차이 : ", diffSecond.toString())
////
////
//    }
}