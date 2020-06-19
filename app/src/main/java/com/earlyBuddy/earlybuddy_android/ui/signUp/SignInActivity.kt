package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivitySignInBinding
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    lateinit var id : String
    lateinit var pw : String

    override val layoutResID: Int
        get() = R.layout.activity_sign_in
//    override val viewModel: SignInViewModel = SignInViewModel(application = EarlyBuddyApplication.globalApplication)
    override val viewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        focusController()
        observe()
        confirmSignIn()

        act_sign_in_et_pw.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_sign_in_et_pw.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
        act_sign_in_iv_auto_login.setOnClickListener {
            if(it.isSelected) it.isSelected = false
            else if(!it.isSelected) it.isSelected = true
        }
        act_sign_in_tv_sign_up.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun focusController(){

        act_sign_in_et_id.setOnFocusChangeListener { view, isFocused ->
            if(isFocused){
                act_sign_in_et_id.setBackgroundResource(R.drawable.border_25_3092ff)
                confirmSignIn()
            }else {
                act_sign_in_et_id.setBackgroundResource(R.drawable.border_25_c3c3c3)
                confirmSignIn()
            }
        }
        act_sign_in_et_pw.setOnFocusChangeListener{ view, isFocused ->
            if(isFocused){
                act_sign_in_et_pw.setBackgroundResource(R.drawable.border_25_3092ff)
                confirmSignIn()
            }else{
                act_sign_in_et_pw.setBackgroundResource(R.drawable.border_25_c3c3c3)
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
            act_sign_in_tv_login.isClickable = true
            act_sign_in_tv_login.setOnClickListener {
                postSignIn()
            }
        } else if (act_sign_in_et_id.text.isEmpty() || act_sign_in_et_pw.text.isEmpty()){
            act_sign_in_tv_login.setBackgroundResource(R.drawable.bg_25_c3c3c3)
            act_sign_in_tv_login.setOnClickListener(null)
            act_sign_in_tv_login.isClickable = false
        }
    }

    fun postSignIn(){
        val jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)
        jsonObject.put("deviceToken", "1")
        val body = JsonParser().parse(jsonObject.toString()) as JsonObject
        viewModel.postSignIn(body)
        Log.e("body ->", body.toString())
    }

    fun observe(){
        viewModel.signInCheck.observe(this, Observer{
            if(it)
                Toast.makeText(this, "환영합니다", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
        })
    }

}
