package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeBusBinding
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.home.pathCheck.HomePathActivity
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.TestPathActivity
import kotlinx.android.synthetic.main.fragment_home_before_bus.*

class BeforeBusFragment : BaseFragment<FragmentHomeBeforeBusBinding, BeforeBusViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_bus
    override val viewModel = BeforeBusViewModel(application = EarlyBuddyApplication.globalApplication)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeResponse = (activity as HomeActivity).viewModel.homeResponse.value

        if (homeResponse != null) {
            viewModel.getData(homeResponse)
        }

        viewDataBinding.vm = viewModel

        frag_home_before_bus__tv_station.isSelected = true

        addObservedData()

        frag_home_before_bus__iv_reboot.setOnClickListener {
            viewModel.timer.cancel()
            (activity as HomeActivity).refresh()
        }

        frag_home_before_bus__iv_detail.setOnClickListener {
            val intent = Intent(activity, HomePathActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addObservedData() {
        viewModel.lastCount.observe(viewLifecycleOwner, Observer {
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

    override fun onPause() {
        super.onPause()
        viewModel.timer.cancel()
    }
}