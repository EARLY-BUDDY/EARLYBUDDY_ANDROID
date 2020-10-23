package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.databinding.ActivityScheduleDetailBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemScheduleDetailWeekdayBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathMethodAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class ScheduleDetailActivity : BaseActivity<ActivityScheduleDetailBinding,ScheduleViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_schedule_detail
    override val viewModel: ScheduleViewModel by viewModel()

    var scheduleIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel

        scheduleIdx = intent.getIntExtra("scheduleIdx", 1)

        viewModel.getPathData(scheduleIdx)

        setLoading()
        setRv()
        setRoute()
        setButton()

    }

    fun setRoute(){

        viewModel.scheduleDetail.observe(this, Observer {
            val scheduleDetailPath = it.path

            var totalTime = ""
            if(scheduleDetailPath.totalTime < 60){
                totalTime = "${scheduleDetailPath.totalTime}분"
            } else {
                totalTime = "${scheduleDetailPath.totalTime/60}시간 ${scheduleDetailPath.totalTime % 60}분"
            }
            viewDataBinding.actScheduleDetailClRoute.actScheduleRouteTime.text = totalTime

            var pathType = ""
            when(scheduleDetailPath.pathType){
                1 -> pathType = "지하철"
                2 -> pathType = "버스"
                3 -> pathType = "지하철 + 버스"
            }
            viewDataBinding.actScheduleDetailClRoute.actScheduleRouteTvMethod.text = pathType

            viewDataBinding.actScheduleDetailClRoute.actScheduleRouteRv.run{

                val pathMethodAdapter = PathMethodAdapter()

                val minWalkLen = round((width * 0.07))
                val minTransLen = round((width * 0.12))
                pathMethodAdapter.minWalkLen = minWalkLen.toInt()
                pathMethodAdapter.minTransLen = minTransLen.toInt()

                val width = width

                var cnt = 0
                for (item in scheduleDetailPath.subPath) {
                    if (item.sectionTime==0) cnt++
                }

                val transSize = scheduleDetailPath.subPath.size/2
                val walkSize = transSize + 1 - cnt
                pathMethodAdapter.totalLen = width - (minWalkLen*walkSize).toInt() - (minTransLen*transSize).toInt()

                adapter = pathMethodAdapter

                pathMethodAdapter.data = scheduleDetailPath.subPath
                pathMethodAdapter.totalTime = scheduleDetailPath.totalTime
                pathMethodAdapter.notifyDataSetChanged()

            }
        })


    }

    fun setRv(){

        viewDataBinding.actScheduleDetailRvDay.run{
            adapter = object : BaseRecyclerViewAdapter<SelectedDay, ItemScheduleDetailWeekdayBinding>(){
                override val layoutResID: Int
                    get() = R.layout.item_schedule_detail_weekday
                override val bindingVariableId: Int
                    get() = BR.weekDay
                override val listener: OnItemClickListener?
                    get() = null
            }

            layoutManager = GridLayoutManager(this@ScheduleDetailActivity, 7)
        }


        viewModel.scheduleDetail.observe(this, Observer {
            var weekDay = arrayListOf(
                SelectedDay("월", false),
                SelectedDay("화", false),
                SelectedDay("수", false),
                SelectedDay("목", false),
                SelectedDay("금", false),
                SelectedDay("토", false),
                SelectedDay("일", false)
            )

            for(i in 0 until weekDay.size){
                if(it.weekdayInfo.contains(i)){
                    weekDay[i].isSelected = true
                }
            }

            (viewDataBinding.actScheduleDetailRvDay.adapter as BaseRecyclerViewAdapter<SelectedDay, *>).run{
                replaceAll(weekDay)
                notifyDataSetChanged()
            }


        })
    }

    private fun setButton(){
        viewDataBinding.actScheduleDetailIvBack.onlyOneClickListener {
            finish()
        }
    }

    private fun setLoading(){
        viewModel.lottieVisible.observe(this, Observer {
            if(it) Loading.goLoading(this@ScheduleDetailActivity)
            else  Loading.exitLoading()
        })

    }
}

data class SelectedDay(
    val text : String,
    var isSelected : Boolean
)