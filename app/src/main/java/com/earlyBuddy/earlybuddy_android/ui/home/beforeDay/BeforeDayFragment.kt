package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.os.Bundle
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeDayBinding

class BeforeDayFragment : BaseFragment<FragmentHomeBeforeDayBinding, BeforeDayViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_day
    override val viewModel = BeforeDayViewModel(application = EarlyBuddyApplication.globalApplication)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeResponse = this.arguments!!.getSerializable("homeResponse") as HomeResponse

        viewModel.getData(homeResponse)

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