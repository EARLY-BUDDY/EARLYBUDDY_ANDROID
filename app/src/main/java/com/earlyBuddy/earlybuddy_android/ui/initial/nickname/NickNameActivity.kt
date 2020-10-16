package com.earlyBuddy.earlybuddy_android.ui.initial.nickname

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityNickNameBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.initial.place.InitialPlaceActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_nick_name.*
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class NickNameActivity : BaseActivity<ActivityNickNameBinding, NickNameViewModel>() {
    override val layoutResID: Int
        get() = R.layout.activity_nick_name
    override val viewModel: NickNameViewModel by viewModel()
    val nicknamePattern: Pattern? = Pattern.compile("^.{0,6}\$", Pattern.CASE_INSENSITIVE)
    var nicknameFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setObserve()
        setListener()


    }


    fun setObserve() {
        viewModel.nickNameResponse.observe(this, Observer {
            if (it.status == 200) {
                val intent = Intent(this, InitialPlaceActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun setListener() {
        viewDataBinding.actInitialNnTvNext.onlyOneClickListener {
            val nickNameBody = JSONObject()

            nickNameBody.put("userName", viewDataBinding.actInitialNnEtNickname.text)
            val body = JsonParser.parseString(nickNameBody.toString()) as JsonObject
            viewModel.putUserNickName(body)
        }

        viewDataBinding.actInitialNnClLayout.onlyOneClickListener {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(act_initial_nn_et_nickname.windowToken, 0)
            viewDataBinding.actInitialNnClLayout.requestFocus()
        }

        act_initial_nn_et_nickname.setOnFocusChangeListener { view, isFocused ->
            if (isFocused && nicknameFlag) {
                act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_3092ff)
            } else if (isFocused && !nicknameFlag) {
                act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_ff6e6e)
            } else if (!isFocused && nicknameFlag) {
                act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_3092ff)
            } else if (!isFocused && !nicknameFlag) {
                act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_ff6e6e)
            }
        }

        act_initial_nn_et_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!nicknamePattern!!.matcher(s.toString()).matches()) {
                    act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    act_initial_nn_tv_warning.text = "6글자 이내로 적어주세요"
                    act_initial_nn_tv_warning.visibility = View.VISIBLE
                    nicknameFlag = false
                } else {
                    act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_3092ff)
                    act_initial_nn_tv_warning.visibility = View.GONE
                    nicknameFlag = true
                }

                if (s!!.isEmpty()) {
                    act_initial_nn_et_nickname.setBackgroundResource(R.drawable.border_25_ff6e6e)
                    act_initial_nn_tv_warning.text = "닉네임을 입력해주세요"
                    act_initial_nn_tv_warning.visibility = View.VISIBLE
                    nicknameFlag = false
                }

                if (nicknameFlag) {
                    act_initial_nn_tv_next.setBackgroundResource(R.drawable.bg_25_3092ff)
                    act_initial_nn_tv_next.isClickable = true
                } else {
                    act_initial_nn_tv_next.setBackgroundResource(R.drawable.bg_25_c3c3c3)
                    act_initial_nn_tv_next.isClickable = false
                }
            }

        })
    }

}
