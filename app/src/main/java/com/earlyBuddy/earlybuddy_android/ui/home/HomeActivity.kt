package com.earlyBuddy.earlybuddy_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeBinding
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.home.pathCheck.HomePathActivity
import kotlinx.android.synthetic.main.activity_home.*
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

        viewModel.getData(true)

//        val asd = intent.getIntExtra("asd", -1)
//        viewModel.getTestData(true, asd)

        act_home_iv_detail.setOnClickListener {
            val intent = Intent(this, HomePathActivity::class.java)
            startActivity(intent)
        }

    }

    fun refresh(loadingVisible:Boolean) {
        viewModel.getData(loadingVisible)
        Log.e("refresh", "리프레시...")
    }

    private fun addObservedData() {

        viewModel.goNoScheduleFragment.observe(this, Observer {
            replaceFragment(it)
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
                "BeforeBusThree" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_threebus)
                "BeforeBusTwo" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_twobus)
                "BeforeBusOne" -> viewDataBinding.actHomeIvBack.setImageResource(R.drawable.img_bg_onebus)
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