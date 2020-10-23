package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import com.earlyBuddy.earlybuddy_android.databinding.ActivityCalendarBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemCalendarScheduleBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarActivity : BaseActivity<ActivityCalendarBinding, CalendarViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_calendar
    override val viewModel : CalendarViewModel by viewModel()

    lateinit var calendarPagerAdapter : CalendarPagerAdapter

    var position = COUNT_PAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        setButton()
        setCalendarVP()
        setScheduleRv()
    }

    private fun setCalendarVP(){
        calendarPagerAdapter = CalendarPagerAdapter(supportFragmentManager).apply {
            setNumOfMonth(COUNT_PAGE)
        }

        viewDataBinding.actCalendarTvMonth.text = calendarPagerAdapter.getMonthDisplayed(position)

        viewDataBinding.actCalendarVp.run{
            adapter = calendarPagerAdapter
            offscreenPageLimit = 3
            currentItem = COUNT_PAGE
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    viewDataBinding.actCalendarTvMonth.text = calendarPagerAdapter.getMonthDisplayed(position)

                    if(position == 0){
                        calendarPagerAdapter.addPrev()
                        setCurrentItem(COUNT_PAGE, false)
                    } else if (position == calendarPagerAdapter.count -1){
                        calendarPagerAdapter.addNext()
                        setCurrentItem(calendarPagerAdapter.count - (COUNT_PAGE + 1), false)
                    }
                    this@CalendarActivity.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }

    }

    private fun setScheduleRv(){
        viewDataBinding.actCalendarScheduleRv.run{
            adapter = object : BaseRecyclerViewAdapter<Schedule, ItemCalendarScheduleBinding>(){
                override val layoutResID: Int
                    get() = R.layout.item_calendar_schedule
                override val bindingVariableId: Int
                    get() = BR.schedule
                override val listener: OnItemClickListener?
                    get() = scheduleClickListener
            }

            layoutManager = LinearLayoutManager(this@CalendarActivity)
        }

    }

    private val scheduleClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener{
        override fun onItemClicked(item: Any?, position: Int?) {
            val intent = Intent(this@CalendarActivity, ScheduleDetailActivity::class.java)
            intent.putExtra("scheduleIdx", (item as Schedule).scheduleIdx)
            startActivity(intent)
        }
    }

    fun showSchedule(schedules : ArrayList<Schedule>){
        (viewDataBinding.actCalendarScheduleRv.adapter as BaseRecyclerViewAdapter<Schedule, *>).apply{
            replaceAll(schedules)
            notifyDataSetChanged()
        }

        if(schedules.size == 0){
            viewDataBinding.actCalendarClEmpty.visibility = VISIBLE
        }else{
            viewDataBinding.actCalendarClEmpty.visibility = GONE
        }
    }

    private fun setButton(){
        viewDataBinding.actCalendarIvBack.onlyOneClickListener {
            finish()
        }

        viewDataBinding.actCalendarIvLeft.onlyOneClickListener {
            viewDataBinding.actCalendarVp.setCurrentItem(position - 1, true)
        }

        viewDataBinding.actCalendarIvRight.onlyOneClickListener {
            viewDataBinding.actCalendarVp.setCurrentItem(position + 1, true)
        }
    }

    companion object {
        private val COUNT_PAGE = 60
    }
}