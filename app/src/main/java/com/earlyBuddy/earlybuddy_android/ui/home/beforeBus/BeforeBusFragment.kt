package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import android.os.Bundle
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeBusBinding

class BeforeBusFragment : BaseFragment<FragmentHomeBeforeBusBinding, BeforeBusViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_bus
    override val viewModel = BeforeBusViewModel(application = EarlyBuddyApplication.globalApplication)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val homeResponse = this.arguments!!.getSerializable("homeResponse") as HomeResponse
        viewModel.getData(homeResponse)

        viewDataBinding.vm = viewModel

        addObservedData()
    }

    fun addObservedData() {
        viewModel.lastCount.observe(this, Observer {
            when (it) {
                1 -> {
                    viewDataBinding.fragHomeBeforeBusIvBack.setImageResource(R.drawable.img_late_bg)
                }
                2 -> {
                    viewDataBinding.fragHomeBeforeBusIvBack.setImageResource(R.drawable.img_twobus)
                }
            }
        })
    }
}