package com.earlyBuddy.earlybuddy_android.ui.myPage.accountManagement

import android.content.Intent
import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityAccountManagementBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.password.PasswordChangeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountManagementActivity :
    BaseActivity<ActivityAccountManagementBinding, AccountmanagementViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_account_management
    override val viewModel: AccountmanagementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actAccountManageTopTitle.actTopTitleTvTitle.text = "계정 관리"

        viewDataBinding.actAccountManageIvPw.onlyOneClickListener {
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

    }
}