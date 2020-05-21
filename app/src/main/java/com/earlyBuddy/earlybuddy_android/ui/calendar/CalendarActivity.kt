package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityCalendarBinding

class CalendarActivity : BaseActivity<ActivityCalendarBinding, CalendarViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_calendar

    override val viewModel = CalendarViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel


        setCalendarRv()

    }

    private fun setCalendarRv(){

    }
}