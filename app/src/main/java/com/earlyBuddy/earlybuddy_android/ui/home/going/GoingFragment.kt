package com.earlyBuddy.earlybuddy_android.ui.home.going

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeGoingBinding
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class GoingFragment : BaseFragment<FragmentHomeGoingBinding, GoingViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_going
    override val viewModel: GoingViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val homeResponse = (requireActivity() as HomeActivity).viewModel.homeResponse.value
        if (homeResponse != null) {
            viewModel.getDate(homeResponse)
        }

        viewDataBinding.vm = viewModel


    }
}