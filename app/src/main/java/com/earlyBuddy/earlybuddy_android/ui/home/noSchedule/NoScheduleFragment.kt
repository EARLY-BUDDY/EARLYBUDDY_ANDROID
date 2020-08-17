package com.earlyBuddy.earlybuddy_android.ui.home.noSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeNoScheduleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NoScheduleFragment : BaseFragment<FragmentHomeNoScheduleBinding, NoScheduleViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_no_schedule
    override val viewModel: NoScheduleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_no_schedule, null)
        return view
    }

}