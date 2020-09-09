package com.earlyBuddy.earlybuddy_android.ui.myPage

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityMyPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_my_page
    override val viewModel: MyPageViewModel by viewModel()
    lateinit var myPageAdapter : MyPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myPageAdapter = MyPageAdapter()
        viewDataBinding.actMyRvMenu.adapter =myPageAdapter

    }
}