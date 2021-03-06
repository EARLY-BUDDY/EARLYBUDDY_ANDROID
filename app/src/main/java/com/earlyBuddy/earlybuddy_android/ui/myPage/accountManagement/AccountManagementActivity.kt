package com.earlyBuddy.earlybuddy_android.ui.myPage.accountManagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.pref.SharedPreferenceController
import com.earlyBuddy.earlybuddy_android.databinding.ActivityAccountManagementBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.MainActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.main.MyPageActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.password.PasswordChangeActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.quit.QuitActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountManagementActivity :
    BaseActivity<ActivityAccountManagementBinding, AccountmanagementViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_account_management
    override val viewModel: AccountmanagementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actAccountManageTopTitle.actTopTitleTvTitle.text = "계정 관리"

        viewDataBinding.actAccountManageEtNickName.text = SharedPreferenceController.getNickName(this)
        viewDataBinding.actAccountManageTvId.text = SharedPreferenceController.getId(this)

        viewDataBinding.actAccountManageTvLogout.onlyOneClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            ActivityCompat.finishAffinity(this)
            startActivity(intent)

            SharedPreferenceController.deleteAuthorization(this)
            SharedPreferenceController.deleteNickName(this)
            SharedPreferenceController.setAutoLogin(this, false)

            finish()
        }

        viewDataBinding.actAccountManageIvQuit.onlyOneClickListener {
            val intent = Intent(this, QuitActivity::class.java)
            startActivity(intent)
        }

        viewDataBinding.actAccountManageTopTitle.actTopTitleIvBack.onlyOneClickListener {
            finish()
        }

        viewDataBinding.actAccountManageIvPw.onlyOneClickListener {
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

    }
}