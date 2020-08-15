package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPlaceBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialPlaceActivity : BaseActivity<ActivityPlaceBinding, InitialPlaceViewModel>() {

    companion object {
        val selectedList = arrayOf(false, false, false)
        var completePlaceCount = 0
    }

    override val layoutResID: Int
        get() = R.layout.activity_place
    override val viewModel: InitialPlaceViewModel by viewModel()

    private val initialPlaceDialogFragment = InitialPlaceDialogFragment().getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCompleteInitialPlace(completePlaceCount)

        viewDataBinding.actInitialPlaceClFirst.onlyOneClickListener {
            initialPlaceDialogFragment.show(supportFragmentManager, "InitialPlace")
        }

    }

    private fun setCompleteInitialPlace(completeCount: Int) {
        when (completeCount) {
            0 -> {
                setClickable(first = true, second = false, third = false)
            }
            1 -> {
                setClickable(first = true, second = true, third = false)
            }
            2 -> {
                setClickable(first = true, second = true, third = true)
            }
        }
    }

    private fun setClickable(first: Boolean, second: Boolean, third: Boolean) {
        viewDataBinding.actInitialPlaceClFirst.isClickable = first
        viewDataBinding.actInitialPlaceClSecond.isClickable = second
        viewDataBinding.actInitialPlaceClThird.isClickable = third
    }
}
