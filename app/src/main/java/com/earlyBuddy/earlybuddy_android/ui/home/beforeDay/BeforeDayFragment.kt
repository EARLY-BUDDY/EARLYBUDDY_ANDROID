package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseTTFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeDayBinding
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity

class BeforeDayFragment : BaseTTFragment<FragmentHomeBeforeDayBinding, BeforeDayViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_day
    lateinit var viewModel: BeforeDayViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(BeforeDayViewModel::class.java)

        val homeResponse = (activity as HomeActivity).viewModel.homeResponse.value
        if (homeResponse != null) {
            viewModel.getData(homeResponse)
        }

        viewDataBinding.vm = viewModel

        addObservedData()
    }

    private fun addObservedData() {
        viewModel.moreThanDay.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewDataBinding.fragHomeBeforeDayIvBack.setImageResource(R.drawable.img_bg_relax)
                viewDataBinding.fragHomeBeforeDayTvBefore.text = "일 전"
            } else {
                viewDataBinding.fragHomeBeforeDayIvBack.setImageResource(R.drawable.img_late_bg)
                viewDataBinding.fragHomeBeforeDayTvBefore.text = "시간 전"
            }
        })
    }

}