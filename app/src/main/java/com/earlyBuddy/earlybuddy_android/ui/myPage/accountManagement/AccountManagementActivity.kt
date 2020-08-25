package com.earlyBuddy.earlybuddy_android.ui.myPage.accountManagement

import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityAccountManagementBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountManagementActivity :
    BaseActivity<ActivityAccountManagementBinding, AccountmanagementViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_account_management
    override val viewModel: AccountmanagementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}