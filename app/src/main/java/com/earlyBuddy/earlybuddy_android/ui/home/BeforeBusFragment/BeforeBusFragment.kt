package com.earlyBuddy.earlybuddy_android.ui.home.BeforeBusFragment

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeBusBinding

class BeforeBusFragment : BaseFragment<FragmentHomeBeforeBusBinding, BeforeBusViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_bus
    override val viewModel: BeforeBusViewModel
        get() = BeforeBusViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.fragHomeBeforeBusClContainer.setBackgroundResource(R.drawable.img_bg_threebus)
    }
}