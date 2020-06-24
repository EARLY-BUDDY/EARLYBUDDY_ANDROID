package com.earlyBuddy.earlybuddy_android.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home
    override val viewModel : HomeViewModel by viewModel()
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservedData()
        viewModel.getData()
    }

    fun refresh() {
        viewModel.getData()
        Log.e("refresh", "리프레시...")
    }

    private fun addObservedData() {

        viewModel.goNoScheduleFragment.observe(this, Observer {
            replaceFragment(it)
        })

        viewModel.goBeforeDayFragment.observe(this, Observer {
            replaceFragment(it)
        })

        viewModel.goBeforBusFragment.observe(this, Observer {
            replaceFragment(it)
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
        refresh()
    }
}