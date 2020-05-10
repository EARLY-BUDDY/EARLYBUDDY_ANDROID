package com.earlyBuddy.earlybuddy_android.ui.home

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomeBinding
import com.earlyBuddy.earlybuddy_android.ui.home.BeforeBusFragment.BeforeBusFragment
import com.earlyBuddy.earlybuddy_android.ui.home.BeforeDayFragment.BeforeDayFragment

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home
    override val viewModel: HomeViewModel
        get() = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var random = (Math.random() * 2).toInt()
        if(random ==1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.act_home_fl_home_fragment,
                    BeforeBusFragment()
                )
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.act_home_fl_home_fragment,
                    BeforeDayFragment()
                )
                .commit()
        }
    }
}
