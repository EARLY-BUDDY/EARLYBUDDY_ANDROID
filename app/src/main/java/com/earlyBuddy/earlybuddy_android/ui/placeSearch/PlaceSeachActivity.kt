package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPlaceSeachBinding

class PlaceSeachActivity : BaseActivity<ActivityPlaceSeachBinding, PlaceSearchViewModel>()  {
    override val layoutResID: Int
        get() = R.layout.activity_place_seach
    override val viewModel: PlaceSearchViewModel
        get() = PlaceSearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
    }


}
