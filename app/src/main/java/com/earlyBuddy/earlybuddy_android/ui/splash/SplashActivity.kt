package com.earlyBuddy.earlybuddy_android.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.pref.SharedPreferenceController
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FirebaseApp.initializeApp(this)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task: Task<InstanceIdResult> ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseSettingEx", "getInstanceId failed", task.exception)
                    return@addOnCompleteListener
                }

                // 토큰을 읽고, 텍스트 뷰에 보여주기
                TransportMap.deviceToken = task.result!!.token
            }

        Handler().postDelayed({
            //아이디 있을 경우. 자동로그인인 경우
//            if (id.isNotEmpty()) {
//                Log.d("test", "id : "+ id) //자동로그인 됨
//                //통신
//                //닉네임 있으면 --> 체크해야함
//                if(nickName.isNotEmpty()){
//                    //홈
//                    goToHomeActivity()
//                    finish()
//                }else{ //닉네임 없으면 설정하러
//                    goToSetNickNameActivity()
//                    finish()
//                }
//            }
//            //아이디 없을 경우. 자동로그인 아닌 경우
//            else{//회원가입하러
//                goToSigninActivity()
//                finish()
//            }
            autoLogin()

        }, SPLASH_TIME_OUT)
        act_splash_av.setAnimation("splash.json")
        act_splash_av.loop(true)
        act_splash_av.playAnimation()
    }

    private fun autoLogin(){

        if(SharedPreferenceController.getAutoLogin(this) && SharedPreferenceController.getAuthorization(this) != ""){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            goToSigninActivity()
        }
    }

    private fun goToSigninActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}