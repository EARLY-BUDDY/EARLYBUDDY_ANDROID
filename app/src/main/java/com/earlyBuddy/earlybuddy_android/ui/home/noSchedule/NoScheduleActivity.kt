package com.earlyBuddy.earlybuddy_android.ui.home.noSchedule

import android.content.Intent
import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeNoScheduleBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.main.MyPageActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleActivity
import org.koin.android.viewmodel.ext.android.viewModel

class NoScheduleActivity : BaseActivity<ActivityHomeNoScheduleBinding, NoScheduleViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_home_no_schedule
    override val viewModel: NoScheduleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actHomeNoIvPlanner.onlyOneClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
        viewDataBinding.actHomeNoClPlus.onlyOneClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
        viewDataBinding.actHomeNoIvWrite.onlyOneClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
        viewDataBinding.actHomeNoIvMyPage.onlyOneClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}