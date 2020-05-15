package com.earlyBuddy.earlybuddy_android.ui.home.beforeBus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.databinding.FragmentHomeBeforeBusBinding

class BeforeBusFragment : BaseFragment<FragmentHomeBeforeBusBinding, BeforeBusViewModel>() {
    override val layoutResID: Int
        get() = R.layout.fragment_home_before_bus
    override val viewModel = BeforeBusViewModel()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()


//      val homeResponse: HomeResponse =
//        this.arguments!!.getSerializable("homeResponse") as HomeResponse
        viewDataBinding.vm = viewModel
//      viewDataBinding.homeResponse = homeResponse


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.planTitle.observe(viewLifecycleOwner, Observer {   Toast.makeText(this.context, "총 " + viewModel.planTitle.value + "개가 검색되었습니다.", Toast.LENGTH_SHORT).show() })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}