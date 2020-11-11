package com.earlyBuddy.earlybuddy_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.home.pathCheck.HomePathActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.main.MyPageActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home
    override val viewModel : HomeViewModel by viewModel()
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        addObservedData()
        setClickListener()

        viewModel.getData(true)

    }

    private fun setClickListener() {
        viewDataBinding.actHomeIvDetail.onlyOneClickListener {
            val intent = Intent(this, HomePathActivity::class.java)
            val data = viewModel.homeResponse.value!!.data!!
            intent.putExtra("scheduleIdx", data.scheduleSummaryData.scheduleIdx)
            intent.putExtra("fromHome", true)


            startActivity(intent)
        }

        viewDataBinding.actHomeIvPlanner.onlyOneClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        viewDataBinding.actHomeIvWrite.onlyOneClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
        viewDataBinding.actHomeIvMyPage.onlyOneClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    fun refresh(loadingVisible: Boolean) {
        viewModel.getData(loadingVisible)
        Log.e("refresh", "리프레시...")
    }

    private fun addObservedData() {


        viewModel.goNoScheduleActivity.observe(this, Observer {
            val intent = Intent(this, it::class.java)
            intent.putExtra("nickName", viewModel.homeResponse.value!!.data!!.userName)
            startActivity(intent)
            finish()
        })

        viewModel.goBeforeDayFragment.observe(this, Observer {
            replaceFragment(it)
        })

        viewModel.goBeforeBusFragment.observe(this, Observer {
            replaceFragment(it)
        })

        viewModel.goGoingFragment.observe(this, Observer {
            replaceFragment(it)
        })

        viewModel.loadingVisibility.observe(this, Observer {
            when (it) {
                true -> {
                    Loading.goLoading(this)
                }
                else -> {
                    Loading.exitLoading()
                }
            }
        })

        viewModel.imageChange.observe(this, Observer {
            when (it) {
                "NoSchedule" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_none)
                "BeforeDay" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_relax)
                "BeforeBusThree" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_late_bg)
                "BeforeBusTwo" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_twobus)
                "BeforeBusOne" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_threebus)
                "Going" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_going)
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.act_home_fl_home_fragment,
                fragment
            ).commit()
        fragment.arguments = bundle
    }

    override fun onRestart() {
        super.onRestart()
        refresh(true)
    }
}