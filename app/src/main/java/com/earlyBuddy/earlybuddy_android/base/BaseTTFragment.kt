package com.earlyBuddy.earlybuddy_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseTTFragment<T : ViewDataBinding, R : BaseViewModel> : Fragment() {
    lateinit var viewDataBinding: T

    abstract val layoutResID: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        viewDataBinding.lifecycleOwner = this@BaseTTFragment
        return viewDataBinding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}