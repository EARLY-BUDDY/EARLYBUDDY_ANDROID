package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.os.Bundle
import android.util.Log
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.repository.SignInRepository
import com.earlyBuddy.earlybuddy_android.databinding.ActivitySignInBinding
import kotlinx.android.synthetic.main.activity_sign_in.*
import com.earlyBuddy.earlybuddy_android.base.BaseActivity as BaseActivity

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    var repository = SignInRepository()
    lateinit var id : String
    lateinit var pw : String

    override val layoutResID: Int
        get() = R.layout.activity_sign_in
    override val viewModel: SignInViewModel = SignInViewModel(repository = SignInRepository())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        focusController()
    }

    private fun focusController(){

        act_sign_in_et_id.setOnFocusChangeListener { view, isFocused ->
            if(isFocused){
                act_sign_in_et_id.setBackgroundResource(R.drawable.bolder_25_3092ff)
                confirmSignIn()
            }else if(!isFocused && act_sign_in_et_id.text.isEmpty()) {
                act_sign_in_et_id.setBackgroundResource(R.drawable.bolder_25_c3c3c3)
                confirmSignIn()
            }
        }
        act_sign_in_et_pw.setOnFocusChangeListener{ view, isFocused ->
            if(isFocused){
                act_sign_in_et_pw.setBackgroundResource(R.drawable.bolder_25_3092ff)
                confirmSignIn()
            }else if(!isFocused && act_sign_in_et_pw.text.isEmpty()){
                act_sign_in_et_pw.setBackgroundResource(R.drawable.bolder_25_c3c3c3)
                confirmSignIn()
            }
        }
    }

    fun confirmSignIn(){
        if (act_sign_in_et_id.text.isNotEmpty() && act_sign_in_et_pw.text.isNotEmpty()) {
            act_sign_in_tv_login.setBackgroundResource(R.drawable.bg_25_3092ff)
            id = act_sign_in_et_id.text.toString()
            pw = act_sign_in_et_pw.text.toString()
            Log.e("id", id)
            Log.e("pw", pw)

        } else if (act_sign_in_et_id.text.isEmpty() || act_sign_in_et_pw.text.isEmpty()){
            act_sign_in_tv_login.setBackgroundResource(R.drawable.bg_25_c3c3c3)
        }
    }

}
