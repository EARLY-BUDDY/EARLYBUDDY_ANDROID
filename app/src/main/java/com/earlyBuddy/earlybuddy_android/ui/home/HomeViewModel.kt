package com.earlyBuddy.earlybuddy_android.ui.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.FirstTrans
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeSchedule
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleSummaryData
import com.earlyBuddy.earlybuddy_android.data.repository.HomeRepository
import com.earlyBuddy.earlybuddy_android.ui.home.beforeBus.BeforeBusFragment
import com.earlyBuddy.earlybuddy_android.ui.home.beforeDay.BeforeDayFragment
import com.earlyBuddy.earlybuddy_android.ui.home.going.GoingFragment
import com.earlyBuddy.earlybuddy_android.ui.home.noSchedule.NoScheduleFragment
import io.reactivex.android.schedulers.AndroidSchedulers

class HomeViewModel(private val repository: HomeRepository) :
    BaseViewModel() {

    val goBeforeBusFragment = MutableLiveData<Fragment>()
    val goBeforeDayFragment = MutableLiveData<Fragment>()
    val goNoScheduleFragment = MutableLiveData<Fragment>()
    val goGoingFragment = MutableLiveData<Fragment>()
    val homeResponse = MutableLiveData<HomeResponse>()
    val loadingVisibility = MutableLiveData<Boolean>()

    private lateinit var tempHomeResponse: HomeResponse


    private val homeResponses: HomeResponse =
        HomeResponse(
            status = 200,
            message = "홈 화면에 보여줄 일정이 없습니다",
            data = HomeSchedule(
                scheduleCheck = 2,
                scheduleSummaryData = ScheduleSummaryData(
                    scheduleIdx = 2,
                    scheduleName = "소풍가기",
                    scheduleStartTime = "2020-07-24 01:15:00",
                    endAddress = "서울 송파구 양재대로 1218"
                ),
                lastTransCount = 3,
                arriveTime = "2020-07-22 10:28:04",
                firstTrans = FirstTrans(
                    detailIdx = 2,
                    trafficType = 1,
                    subwayLane = 6,
                    busNo = null,
                    busType = null,
                    startName = "화랑대"
                ),
                nextTransArriveTime = "2020-07-22 10:44:04"
            )
        )

    fun getTestData(loadingVisible: Boolean, asd: Int) {
        if (loadingVisible) {
            loadingVisibility.value = true
        }
        addDisposable(repository.getTestHomeData(asd).observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {}
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {
                if (loadingVisible) {
                    loadingVisibility.value = false
                }
                homeResponse.value = tempHomeResponse
//                homeResponse.value = homeResponses

                when (tempHomeResponse.data!!.scheduleCheck) {
                    1 -> {
                        goNoScheduleFragment.value = NoScheduleFragment()
                    }
                    2 -> {
                        goBeforeDayFragment.value = BeforeDayFragment()
                    }
                    3 -> {
                        goBeforeBusFragment.value = BeforeBusFragment()
                    }
                    else -> {
                        goGoingFragment.value = GoingFragment()
                    }
                }
            }
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                // onResponse
                tempHomeResponse = it!!
                Log.e("Asdasd", tempHomeResponse.toString())
            }) {
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }

    fun getData(loadingVisible: Boolean) {
        if (loadingVisible) {
            loadingVisibility.value = true
        }
        addDisposable(repository.getHomeData().observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {}
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {
                if (loadingVisible) {
                    loadingVisibility.value = false
                }
                homeResponse.value = tempHomeResponse
//                homeResponse.value = homeResponses

                when (tempHomeResponse.data!!.scheduleCheck) {
                    1 -> {
                        goNoScheduleFragment.value = NoScheduleFragment()
                    }
                    2 -> {
                        goBeforeDayFragment.value = BeforeDayFragment()
                    }
                    else -> {
                        goBeforeBusFragment.value = BeforeBusFragment()
                    }
                }
             }
             // 옵서버블을 구독
             .subscribe({
                 // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                 // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                 // onResponse
                 tempHomeResponse = it!!
                 Log.e("Asdasd", tempHomeResponse.toString())
             }) {
                 // 에러 블록
                 // 네트워크 오류나 데이터 처리 오류 등
                 // 작업이 정상적으로 완료되지 않았을 때 호출

                 // onFailure
                 Log.e("통신 실패 error : ", it.message!!)
             })
    }

}