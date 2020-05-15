package com.earlyBuddy.earlybuddy_android.ui.home

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.FirstTrans
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeSchedule
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleSummaryData
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeBinding
import com.earlyBuddy.earlybuddy_android.ui.home.beforeBus.BeforeBusFragment
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home
    override val viewModel: HomeViewModel
        get() = HomeViewModel()
    private val beforeBusFragment = BeforeBusFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeResponse: HomeResponse =
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

        val bundle = Bundle()

        bundle.putSerializable("homeResponse", homeResponse)
        when (homeResponse.message) {
            "홈 화면에 보여줄 일정이 없습니다" -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_home_fl_home_fragment,
                        beforeBusFragment
                    ).commit()
                beforeBusFragment.arguments = bundle
            }
        }
//        var random = (Math.random() * 2).toInt()
//        if(random ==1) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.act_home_fl_home_fragment,
//                    BeforeBusFragment()
//                )
//                .commit()
//        }else{
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.act_home_fl_home_fragment,
//                    BeforeDayFragment()
//                )
//                .commit()
//        }
    }
}

fun main() {

        val date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd")
        print(format.format(date))
    }


