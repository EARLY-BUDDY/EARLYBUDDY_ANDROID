package com.earlyBuddy.earlybuddy_android.ui.myPage.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPasswordChangeBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class PasswordChangeActivity : BaseActivity<ActivityPasswordChangeBinding, PasswordViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_password_change
    override val viewModel: PasswordViewModel by viewModel()
    val pwPattern: Pattern? =
        Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}", Pattern.CASE_INSENSITIVE)
    var pwFlag: Boolean = false
    var pwCheckFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewDataBinding.actPasswordChangeTopTitle.actTopTitleTvTitle.text = "비밀번호 변경"

        viewDataBinding.actPasswordChangeEtPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!(pwPattern!!.matcher(s.toString()).matches())) {
                    viewDataBinding.actPasswordChangeTvWarnPw.visibility = View.VISIBLE
                    pwFlag = false
                } else {
                    viewDataBinding.actPasswordChangeTvWarnPw.visibility = View.INVISIBLE
                    pwFlag = true
                }
                buttonActive()
            }
        })

        viewDataBinding.actPasswordChangeEtPwConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != viewDataBinding.actPasswordChangeEtPw.text.toString()) {
                    viewDataBinding.actPasswordChangeTvWarnPwConfirm.visibility = View.VISIBLE
                    pwCheckFlag = false
                } else {
                    viewDataBinding.actPasswordChangeTvWarnPwConfirm.visibility = View.INVISIBLE
                    pwCheckFlag = true
                }
                buttonActive()
            }
        })
    }

    fun buttonActive() {
        if (pwFlag && pwCheckFlag) {
            viewDataBinding.actPasswordChangeTvChange.setBackgroundResource(R.drawable.bg_25_3092ff)
            viewDataBinding.actPasswordChangeTvChange.isClickable = true
            viewDataBinding.actPasswordChangeTvChange.onlyOneClickListener {
                Log.e("비밀번호 변경!", "통신")
            }
        } else {
            viewDataBinding.actPasswordChangeTvChange.setBackgroundResource(R.drawable.bg_25_c3c3c3)
            viewDataBinding.actPasswordChangeTvChange.isClickable = false
            viewDataBinding.actPasswordChangeTvChange.onlyOneClickListener {
                null
            }
        }
    }
}