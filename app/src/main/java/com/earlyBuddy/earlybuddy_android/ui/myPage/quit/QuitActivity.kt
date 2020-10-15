package com.earlyBuddy.earlybuddy_android.ui.myPage.quit

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityQuitBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuitActivity : BaseActivity<ActivityQuitBinding, QuitViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_quit
    override val viewModel: QuitViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actQuitTopTitle.actTopTitleTvTitle.text = "회원 탈퇴"
        viewDataBinding.actQuitTopTitle.actTopTitleIvBack.onlyOneClickListener {
            finish()
        }

    }


}