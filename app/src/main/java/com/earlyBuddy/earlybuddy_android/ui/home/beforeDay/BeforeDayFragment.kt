package com.earlyBuddy.earlybuddy_android.ui.home.beforeDay

import android.os.Bundle
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeDayBinding
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class BeforeDayFragment : BaseFragment<FragmentHomeBeforeDayBinding, BeforeDayViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_day
    override val viewModel: BeforeDayViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val homeResponse = (requireActivity() as HomeActivity).viewModel.homeResponse.value
        if (homeResponse != null) {
            viewModel.getData(homeResponse)
        }

        viewDataBinding.vm = viewModel

        addObservedData()

    }

    private fun addObservedData() {
//        viewModel.moreThanDay.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                viewDataBinding.fragHomeBeforeDayIvBack.setImageResource(R.drawable.img_bg_relax)
//                viewDataBinding.fragHomeBeforeDayTvBefore.text = "일 전"
//            } else {
//                viewDataBinding.fragHomeBeforeDayIvBack.setImageResource(R.drawable.img_late_bg)
//                viewDataBinding.fragHomeBeforeDayTvBefore.text = "시간 전"
//            }
//        })

        viewModel.timeDifference.observe(viewLifecycleOwner, Observer {
            if (it == -1) {
                // 24시간이 넘는데 약속 1일전이다!
                viewDataBinding.fragHomeBeforeDayTvDay.text = "1"
            } else {
                viewDataBinding.fragHomeBeforeDayTvDay.text = it.toString()
            }
        })
    }

}