package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeBusBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.home.pathCheck.HomePathActivity
import kotlinx.android.synthetic.main.fragment_home_before_bus.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class BeforeBusFragment : BaseFragment<FragmentHomeBeforeBusBinding, BeforeBusViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_bus
    override val viewModel: BeforeBusViewModel by viewModel()

    val timer = Timer()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeResponse = (activity as HomeActivity).viewModel.homeResponse.value

        if (homeResponse != null) {
            viewModel.getData(homeResponse)
        }

        viewDataBinding.vm = viewModel

        frag_home_before_bus__tv_station.isSelected = true

        addObservedData()


        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                timer.cancel()
                (activity as HomeActivity).refresh(false)
            }
        }, 7000, 7000)

        frag_home_before_bus__iv_reboot.onlyOneClickListener {
            viewModel.timer.cancel()
            (activity as HomeActivity).refresh(true)
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
        viewModel.nextArriveStop.observe(viewLifecycleOwner, Observer {
            viewDataBinding.fragHomeBeforeBusTvNextTime.text = "다음 배차는 없습니다."
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.timer.cancel()
        timer.cancel()
    }
}