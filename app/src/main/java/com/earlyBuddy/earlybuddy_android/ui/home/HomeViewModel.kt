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

     fun getData() {
        val tempHomeResponse: HomeResponse =
            HomeResponse(
                status = 200,
                message = "홈 화면에 보여줄 일정이 없습니다",
                data = HomeSchedule(
                    scheduleCheck = 3,
                    ready = true,
                    scheduleSummaryData = ScheduleSummaryData(
                        scheduleIdx = 3,
                        scheduleName = "집가기",
                        scheduleStartTime = "2020-06-08 22:20:00",
                        endAddress = "서울 오륜동 올림픽선수촌 아파트"
                    ),
                    lastTransCount = 1,
                    arriveTime = "2020-06-08 22:20:00",
                    firstTrans = FirstTrans(
                        detailIdx = 2,
                        trafficType = 1, // 1 -> 지하철 2 -> 버스
                        subwayLane = 7,
                        busNo = null,
                        busType = null,
                        detailStartAddress = "공릉역"
                    ),
                    nextTransArriveTime = "2020-06-08 22:30:00"
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

}