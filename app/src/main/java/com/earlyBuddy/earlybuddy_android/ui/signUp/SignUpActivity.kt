package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivitySignUpBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.myPage.license.LicenseActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_sign_up
    override val viewModel: SignUpViewModel by viewModel()

    val pwPattern: Pattern? =
        Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}", Pattern.CASE_INSENSITIVE)
    var pwFlag: Boolean = false
    var pwCheckFlag: Boolean = false
    var termsCheckFlag: Boolean = false
    var id: String = ""
    var pw: String = ""
    var pwCheck: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        viewDataBinding.actSignUpIvTermsSecond.text = Html.fromHtml("<u>개인정보처리방침</u>")
        viewDataBinding.actSignUpIvTermsFirst.text = Html.fromHtml("<u>이용약관</u>")
        pwCheck()
        pwSameCheck()
        focusController()
//        confirmJoin()
        setEditTextChange()
//        setButton()
        observe()
        onClickTerms()
//        viewDataBinding.actSignUpTvPwCheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                v.clearFocus()
//                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                keyboard.hideSoftInputFromWindow(act_sign_up_et_pw_check.windowToken, 0)
//                return@OnKeyListener true
//            }
//            false
//        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

//    var signUpDialogFragmentDismissListener = object : SignUpDialogFragment.OnDialogDismissedListener {
//        override fun onDialogDismissed() {
//            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun pwCheck() {
        viewDataBinding.actSignUpEtPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!(pwPattern!!.matcher(p0.toString()).matches())) {
                    viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    viewDataBinding.actSignUpTvPwWarning.visibility = View.VISIBLE
                    pwFlag = false
                    pw = p0.toString()
                } else {
                    viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_3092ff)
                    viewDataBinding.actSignUpTvPwWarning.visibility = View.INVISIBLE
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
                    viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    viewDataBinding.actSignUpTvPwCheckWarning.visibility = View.VISIBLE
                    pwCheckFlag = false
                    pwCheck = p0.toString()
                } else if (p0.toString() == pw) {
                    Log.e("비밀번호는 -> ", pw)
                    Log.e("입력중인 비밀번호는 -> ", p0.toString())
                    viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_3092ff)
                    viewDataBinding.actSignUpTvPwCheckWarning.visibility = View.INVISIBLE
                    pwCheckFlag= true
                    pwCheck = p0.toString()
                }
            }
        })
    }

    private fun focusController(){
        viewDataBinding.actSignUpEtId.setOnFocusChangeListener { view, isFocused ->
            if(isFocused){
                viewDataBinding.actSignUpEtId.setBackgroundResource(R.drawable.border_25_3092ff)
                viewDataBinding.actSignUpTvIdWarning.visibility = View.INVISIBLE
            } else{
                viewDataBinding.actSignUpEtId.setBackgroundResource(R.drawable.border_25_c3c3c3)
            }
        }


        viewDataBinding.actSignUpEtPw.setOnFocusChangeListener { view, isFocused ->
            if (!isFocused && !pwFlag){ //포커스 잃고 비밀번호 유효하지 않은 경우
                Log.e("포커스 잃고 유효하지 않은", pwFlag.toString())
                viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_ff6e6e)
//                confirmJoin()
            } else if (!isFocused && pwFlag){ //포커스 잃고 비밀번호 유효한 경우
                Log.e("포커스 잃고 유효", pwFlag.toString())
                viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_c3c3c3)
//                confirmJoin()
            } else if (isFocused && !pwFlag) { //포커스 얻은 상태에서 비밀번호 유효하지 않은 경우
                Log.e("포커스 얻고 유효하지 않은", pwFlag.toString())
                viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_ff6e6e)
                viewDataBinding.actSignUpTvPwWarning.visibility = View.VISIBLE
//                confirmJoin()
            } else if (isFocused && pwFlag){ //포커스 얻은 상태에서 비밀번호 유효한 경우
                Log.e("포커스 얻고 유효", pwFlag.toString())
                viewDataBinding.actSignUpEtPw.setBackgroundResource(R.drawable.border_25_3092ff)
//                confirmJoin()
            }
        }

        viewDataBinding.actSignUpEtPwCheck.setOnFocusChangeListener { view, isFocused ->
            if (!isFocused && !pwCheckFlag){ //포커스 잃고 비밀번호 유효하지 않은 경우
                viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_ff6e6e)
//                confirmJoin()
            } else if (!isFocused && pwCheckFlag){ //포커스 잃고 비밀번호 유효한 경우
                viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_c3c3c3)
//                confirmJoin()
            } else if (isFocused && !pwCheckFlag) { //포커스 얻은 상태에서 비밀번호 유효하지 않은 경우
                viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_ff6e6e)
                viewDataBinding.actSignUpTvPwCheckWarning.visibility = View.VISIBLE
//                confirmJoin()
            } else if (isFocused && pwCheckFlag){ //포커스 얻은 상태에서 비밀번호 유효한 경우
                viewDataBinding.actSignUpEtPwCheck.setBackgroundResource(R.drawable.border_25_3092ff)
//                confirmJoin()
            }
        }
    }

    private fun confirmJoin(){
        if (viewDataBinding.actSignUpEtId.text.isNotEmpty() && pwFlag && pwCheckFlag) {
            viewDataBinding.actSignUpTvRegist.setBackgroundResource(R.drawable.bg_25_3092ff)
            id = act_sign_up_et_id.text.toString()
            viewDataBinding.actSignUpTvRegist.isClickable = true
            viewDataBinding.actSignUpTvRegist.onlyOneClickListener {
                postSignUp()
            }
        } else if(viewDataBinding.actSignUpEtId.text.isEmpty() || viewDataBinding.actSignUpEtPw.text.isEmpty() || viewDataBinding.actSignUpEtPwCheck.text.isEmpty()
            || !pwFlag || !pwCheckFlag){
            viewDataBinding.actSignUpTvRegist.setBackgroundResource(R.drawable.bg_25_c3c3c3)
            viewDataBinding.actSignUpTvRegist.setOnClickListener(null)
            viewDataBinding.actSignUpTvRegist.isClickable = false
        }
    }

    fun postSignUp(){

        val jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)
        jsonObject.put("deviceToken", TransportMap.deviceToken)
        Log.e("아이디 비번", "$id $pw $pwCheck")

        val body = JsonParser.parseString(jsonObject.toString()) as JsonObject
        viewModel.postSignUp(body)
    }

    fun observe(){
        viewModel.signUpCheck.observe(this, Observer {
            if (it == "회원 가입 성공") {
                Toast.makeText(this, "얼리버디의 회원이 되셨습니다", Toast.LENGTH_SHORT).show()
//                signUpDialog.show(supportFragmentManager,"signUp_fagment")
//                val intent = Intent(this, SignInActivity::class.java)
//                startActivity(intent)
                finish()
            } else if (it == "이미 사용중인 아이디입니다.") {
                viewDataBinding.actSignUpTvIdWarning.visibility = View.VISIBLE
                viewDataBinding.actSignUpEtId.setBackgroundResource(R.drawable.border_25_ff6e6e)
            } else if (it == "입력되지 않은 값이 있습니다") {
                Toast.makeText(this, "입력되지 않은 값이 있습니다", Toast.LENGTH_SHORT).show()
            }
        })

//        viewModel.netWork.observe(this, Observer {
//            Toast.makeText(this, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show()
//        })
    }

    private fun getEditText() {
        id = viewDataBinding.actSignUpEtId.text.toString()
        pw = viewDataBinding.actSignUpEtPw.text.toString()
        pwCheck = viewDataBinding.actSignUpEtPwCheck.text.toString()
    }

    private fun onDataCheck() {
        getEditText()

        if (viewDataBinding.actSignUpEtId.text.isNotEmpty() && pwFlag && pwCheckFlag
            && pw == pwCheck && termsCheckFlag) {

            viewDataBinding.actSignUpTvRegist.setBackgroundResource(R.drawable.bg_25_3092ff)
            viewDataBinding.actSignUpTvRegist.isClickable = true
            viewDataBinding.actSignUpTvRegist.onlyOneClickListener {
                postSignUp()
            }
        } else {

            viewDataBinding.actSignUpTvRegist.setBackgroundResource(R.drawable.bg_25_c3c3c3)
            viewDataBinding.actSignUpTvRegist.setOnClickListener(null)
            viewDataBinding.actSignUpTvRegist.isClickable = false
        }

    }

    private fun setEditTextChange() {
        viewDataBinding.actSignUpEtId.onChange { onDataCheck() }
        viewDataBinding.actSignUpEtPw.onChange { onDataCheck() }
        viewDataBinding.actSignUpEtPwCheck.onChange { onDataCheck() }
    }

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    private fun onClickTerms(){
        viewDataBinding.actSignUpIvCheck.onlyOneClickListener {
            when(termsCheckFlag){
                false -> {
                    termsCheckFlag = true
                    viewDataBinding.actSignUpIvCheck.setImageResource(R.drawable.ic_check_selected)
                }
                true -> {
                    termsCheckFlag = false
                    viewDataBinding.actSignUpIvCheck.setImageResource(R.drawable.ic_check_unselected)
                }
            }
            onDataCheck()
        }

        viewDataBinding.actSignUpEtPwCheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(v.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

        viewDataBinding.actSignUpIvTermsFirst.onlyOneClickListener {
            val intent = Intent(this, LicenseActivity::class.java)
            intent.putExtra("title", "이용약관")
            startActivity(intent)
        }

        viewDataBinding.actSignUpIvTermsSecond.onlyOneClickListener {
            val intent = Intent(this, LicenseActivity::class.java)
            intent.putExtra("title", "개인정보처리방침")
            startActivity(intent)
        }
    }
//    fun setButton(){
//        viewDataBinding.actSignUpClBg.setOnClickListener {
//            hideKeyboard(viewDataBinding.actSignUpEtId)
//        }
//    }

    //    fun hideKeyboard(et: EditText){
//
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(et.windowToken, 0)
//
//    }
    fun hideKeyboard(et: EditText) {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
    }

}
