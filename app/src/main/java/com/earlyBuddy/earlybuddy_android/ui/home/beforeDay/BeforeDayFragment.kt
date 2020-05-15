package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeDayBinding

class BeforeDayFragment : BaseFragment<FragmentHomeBeforeDayBinding, BeforeDayViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override val layoutResID: Int
        get() = R.layout.fragment_home_before_day
    override val viewModel: BeforeDayViewModel
        get() = BeforeDayViewModel()
}