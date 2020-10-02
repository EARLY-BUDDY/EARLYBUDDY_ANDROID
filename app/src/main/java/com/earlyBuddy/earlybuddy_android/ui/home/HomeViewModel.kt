package com.earlyBuddy.earlybuddy_android.ui.home

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.data.repository.HomeRepository
import com.earlyBuddy.earlybuddy_android.ui.home.beforeBus.BeforeBusFragment
import com.earlyBuddy.earlybuddy_android.ui.home.beforeDay.BeforeDayFragment
import com.earlyBuddy.earlybuddy_android.ui.home.going.GoingFragment
import com.earlyBuddy.earlybuddy_android.ui.home.noSchedule.NoScheduleActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat

class HomeViewModel(private val repository: HomeRepository) :
    BaseViewModel() {

    val goBeforeBusFragment = MutableLiveData<Fragment>()
    val goBeforeDayFragment = MutableLiveData<Fragment>()
    val goNoScheduleActivity = MutableLiveData<Activity>()
    val goGoingFragment = MutableLiveData<Fragment>()
    val homeResponse = MutableLiveData<HomeResponse>()
    val loadingVisibility = MutableLiveData<Boolean>()
    val startTime = MutableLiveData<String>()
    val imageChange = MutableLiveData<String>()

    private lateinit var tempHomeResponse: HomeResponse


//    private val homeResponses: HomeResponse =
//        HomeResponse(
//            status = 200,
//            message = "홈 화면에 보여줄 일정이 없습니다",
//            data = HomeSchedule(
//                scheduleCheck = 2,
//                scheduleSummaryData = ScheduleSummaryData(
//                    scheduleIdx = 2,
//                    scheduleName = "소풍가기",
//                    scheduleStartTime = "2020-07-24 01:15:00",
//                    endAddress = "서울 송파구 양재대로 1218"
//                ),
//                lastTransCount = 3,
//                arriveTime = "2020-07-22 10:28:04",
//                firstTrans = FirstTrans(
//                    detailIdx = 2,
//                    trafficType = 1,
//                    subwayLane = 6,
//                    busNo = null,
//                    busType = null,
//                    startName = "화랑대"
//                ),
//                nextTransArriveTime = "2020-07-22 10:44:04"
//            )
//        )

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
                        goNoScheduleActivity.value = NoScheduleActivity()
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
                        goNoScheduleActivity.value = NoScheduleActivity()
                    }
                    2 -> {
                        goBeforeDayFragment.value = BeforeDayFragment()
                        getTimeDifference(tempHomeResponse)
                        getBackGroundImage(tempHomeResponse)
                    }
                    3 -> {
                        goBeforeBusFragment.value = BeforeBusFragment()
                        getTimeDifference(tempHomeResponse)
                        getBackGroundImage(tempHomeResponse)
                    }
                    else -> {
                        goGoingFragment.value = GoingFragment()
                        getTimeDifference(tempHomeResponse)
                        getBackGroundImage(tempHomeResponse)
                    }
                }
            }
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                // onResponse
                tempHomeResponse = it!!
                Log.e("홈 통신 결과", tempHomeResponse.toString())
            }) {
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("홈에서 통신 실패 error : ", it.message!!)
            })
    }

    private fun getTimeDifference(tempHomeResponse: HomeResponse) {
        val scheduleStartTime =
            tempHomeResponse.data!!.scheduleSummaryData.scheduleStartTime

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val promiseStartTime = sdf.parse(scheduleStartTime)

        if (promiseStartTime.hours >= 12) {
            startTime.value =
                "오후 ${String.format("%02d:%02d", promiseStartTime.minutes, promiseStartTime.hours)}"
        } else {
            startTime.value =
                "오전 ${String.format("%02d:%02d", promiseStartTime.minutes, promiseStartTime.hours)}"
        }
    }

    private fun getBackGroundImage(tempHomeResponse: HomeResponse) {
        when (tempHomeResponse.data!!.scheduleCheck) {
            1 -> {
                imageChange.value = "NoSchedule"
            }
            2 -> {
                imageChange.value = "BeforeDay"
            }
            3 -> {
                when (tempHomeResponse.data.lastTransCount) {
                    3 -> {
                        imageChange.value = "BeforeBusThree"
                    }
                    2 -> {
                        imageChange.value = "BeforeBusTwo"
                    }
                    1 -> {
                        imageChange.value = "BeforeBusOne"
                    }
                }
            }
            4 -> {
                imageChange.value = "Going"
            }
        }
    }
}