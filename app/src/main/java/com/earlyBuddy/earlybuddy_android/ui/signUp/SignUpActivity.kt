package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivitySignUpBinding
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_sign_up

//    override val viewModel: SignUpViewModel = SignUpViewModel(application = EarlyBuddyApplication.globalApplication)
    override val viewModel: SignUpViewModel by viewModel()

    val pwPattern: Pattern? = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}", Pattern.CASE_INSENSITIVE)
    var pwFlag: Boolean = false
    var pwCheckFlag: Boolean = false
    lateinit var id: String
    lateinit var pw: String
    lateinit var pwCheck: String
//    lateinit var signUpDialog : SignUpDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewDataBinding.vm = viewModel
//        signUpDialog = SignUpDialogFragment()
//        signUpDialog.setOnDialogDismissedListener(signUpDialogFragmentDismissListener)

        pwCheck()
        pwSameCheck()
        focusController()
        confirmJoin()
        observe()
        act_sign_up_et_pw_check.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_sign_up_et_pw_check.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

//    var signUpDialogFragmentDismissListener = object : SignUpDialogFragment.OnDialogDismissedListener {
//        override fun onDialogDismissed() {
//            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun pwCheck(){
        act_sign_up_et_pw.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!(pwPattern!!.matcher(p0.toString()).matches())) {
                    act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    act_sign_up_tv_pw_warning.visibility = View.VISIBLE
                    pwFlag = false
                    pw = p0.toString()
                }else{
                    act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_3092ff)
                    act_sign_up_tv_pw_warning.visibility = View.INVISIBLE
                    pwFlag = true
                    pw = p0.toString()
                }
            }
        })
    }

    private fun pwSameCheck(){
        act_sign_up_et_pw_check.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != pw){
                    Log.e("비밀번호는 -> ", pw)
                    Log.e("입력중인 비밀번호는 -> ", p0.toString())
                    act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    act_sign_up_tv_pw_check_warning.visibility = View.VISIBLE
                    pwCheckFlag = false
                    pwCheck = p0.toString()
                } else if (p0.toString() == pw) {
                    Log.e("비밀번호는 -> ", pw)
                    Log.e("입력중인 비밀번호는 -> ", p0.toString())
                    act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_3092ff)
                    act_sign_up_tv_pw_check_warning.visibility = View.INVISIBLE
                    pwCheckFlag= true
                    pwCheck = p0.toString()
                }
            }
        })
    }

    private fun focusController(){
        act_sign_up_et_id.setOnFocusChangeListener { view, isFocused ->
            if(isFocused){
                act_sign_up_et_id.setBackgroundResource(R.drawable.border_25_3092ff)
                act_sign_up_tv_id_warning.visibility = View.INVISIBLE
            } else{
                act_sign_up_et_id.setBackgroundResource(R.drawable.border_25_c3c3c3)
            }
        }

        act_sign_up_et_pw.setOnFocusChangeListener { view, isFocused ->
            if (!isFocused && !pwFlag){ //포커스 잃고 비밀번호 유효하지 않은 경우
                Log.e("포커스 잃고 유효하지 않은", pwFlag.toString())
                act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_ff6e6e)
                confirmJoin()
            } else if (!isFocused && pwFlag){ //포커스 잃고 비밀번호 유효한 경우
                Log.e("포커스 잃고 유효", pwFlag.toString())
                act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_c3c3c3)
                confirmJoin()
            } else if (isFocused && !pwFlag) { //포커스 얻은 상태에서 비밀번호 유효하지 않은 경우
                Log.e("포커스 얻고 유효하지 않은", pwFlag.toString())
                act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_ff6e6e)
                act_sign_up_tv_pw_warning.visibility = View.VISIBLE
                confirmJoin()
            } else if (isFocused && pwFlag){ //포커스 얻은 상태에서 비밀번호 유효한 경우
                Log.e("포커스 얻고 유효", pwFlag.toString())
                act_sign_up_et_pw.setBackgroundResource(R.drawable.border_25_3092ff)
                confirmJoin()
            }
        }

        act_sign_up_et_pw_check.setOnFocusChangeListener { view, isFocused ->
            if (!isFocused && !pwCheckFlag){ //포커스 잃고 비밀번호 유효하지 않은 경우
                act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_ff6e6e)
                confirmJoin()
            } else if (!isFocused && pwCheckFlag){ //포커스 잃고 비밀번호 유효한 경우
                act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_c3c3c3)
                confirmJoin()
            } else if (isFocused && !pwCheckFlag) { //포커스 얻은 상태에서 비밀번호 유효하지 않은 경우
                act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_ff6e6e)
                act_sign_up_tv_pw_check_warning.visibility = View.VISIBLE
                confirmJoin()
            } else if (isFocused && pwCheckFlag){ //포커스 얻은 상태에서 비밀번호 유효한 경우
                act_sign_up_et_pw_check.setBackgroundResource(R.drawable.border_25_3092ff)
                confirmJoin()
            }
        }
    }

    private fun confirmJoin(){
        if (act_sign_up_et_id.text.isNotEmpty() && pwFlag && pwCheckFlag) {
            act_sign_up_tv_regist.setBackgroundResource(R.drawable.bg_25_3092ff)
            id = act_sign_up_et_id.text.toString()
            act_sign_up_tv_regist.isClickable = true
            act_sign_up_tv_regist.setOnClickListener {
                postSignUp()
            }
        } else if(act_sign_up_et_id.text.isEmpty() || act_sign_up_et_pw.text.isEmpty() || act_sign_up_et_pw_check.text.isEmpty()
            || !pwFlag || !pwCheckFlag){
            act_sign_up_tv_regist.setBackgroundResource(R.drawable.bg_25_c3c3c3)
            act_sign_up_tv_regist.setOnClickListener(null)
            act_sign_up_tv_regist.isClickable = false
        }
    }

    fun postSignUp(){
        val jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)
        jsonObject.put("deviceToken", 1)
        Log.e("ㅇㅏ이디 비번", "$id $pw $pwCheck")

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject
        viewModel.postSignUp(body)
    }

    fun observe(){
        viewModel.signUpCheck.observe(this, Observer {
            if(it){
                Toast.makeText(this, "얼리버디의 회원이 되셨습니다", Toast.LENGTH_SHORT).show()
//                signUpDialog.show(supportFragmentManager,"signUp_fagment")
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                act_sign_up_tv_id_warning.visibility = View.VISIBLE
                act_sign_up_et_id.setBackgroundResource(R.drawable.border_25_ff6e6e)
            }
        })

//        viewModel.netWork.observe(this, Observer {
//            Toast.makeText(this, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show()
//        })
    }

}
