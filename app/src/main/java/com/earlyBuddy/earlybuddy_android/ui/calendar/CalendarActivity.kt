package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityCalendarBinding

class CalendarActivity : BaseActivity<ActivityCalendarBinding, CalendarViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_calendar
    override val viewModel = CalendarViewModel()

    var position = COUNT_PAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        setButton()
        setCalendarVP()
    }

    private fun setCalendarVP(){
        var calendarPagerAdapter = CalendarPagerAdapter(supportFragmentManager).apply {
            setNumOfMonth(COUNT_PAGE)
        }

        viewDataBinding.actCalendarTvMonth.text = calendarPagerAdapter.getMonthDisplayed(position)

        viewDataBinding.actCalendarVp.run{
            adapter = calendarPagerAdapter
            currentItem = COUNT_PAGE
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {

                }

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

    private fun setButton(){
        viewDataBinding.actCalendarIvBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private val COUNT_PAGE = 60
    }
}