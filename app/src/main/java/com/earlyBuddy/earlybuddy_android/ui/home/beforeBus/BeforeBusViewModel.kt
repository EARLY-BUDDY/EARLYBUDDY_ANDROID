package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.FirstTrans
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeSchedule
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleSummaryData

class BeforeBusViewModel : BaseViewModel() {
    private val _planTitle = MutableLiveData<String>()
    val planTitle: LiveData<String> get() = _planTitle
    private val _homeResponse = MutableLiveData<HomeResponse>()
    val homeResponse: LiveData<HomeResponse> get() = _homeResponse

    fun getData() {
        val tempHomeResponse: HomeResponse =
            HomeResponse(
                status = 200,
                message = "홈 화면에 보여줄 일정이 없습니다",
                data = HomeSchedule(
                    ready = true,
                    scheduleSummaryData = ScheduleSummaryData(
                        scheduleIdx = 1,
                        scheduleName = "소풍가기",
                        scheduleStartTime = "2019-12-31 12:15:00",
                        endAddress = "서울 송파구 양재대로 1218"
                    ),
                    lastTransCount = 3,
                    arriveTime = "2019-12-31 11:28:04",
                    firstTrans = FirstTrans(
                        detailIdx = 2,
                        trafficType = 1,
                        subwayLane = 6,
                        busNo = null,
                        busType = null,
                        detailStartAddress = "화랑대"
                    ),
                    nextTransArriveTime = "2019-12-31 11:35:04"
                )
            )
        _homeResponse.value = tempHomeResponse
    }

}