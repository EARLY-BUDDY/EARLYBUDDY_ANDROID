package com.earlyBuddy.earlybuddy_android.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import kotlinx.android.synthetic.main.activity_loading.*

object Loading {
     var activity: Activity = LoadingActivity()

    fun goLoading(nowActivityName: Context) {
        val intent = Intent(nowActivityName, LoadingActivity::class.java)
        nowActivityName.startActivity(intent)
        Log.e("로딩", "시작~~~~~~~")
    }

    fun exitLoading() {
        val handler = Handler()
        handler.postDelayed({
            Log.e("로딩", "끝ㅠㅠㅠㅠㅠㅠ")
            activity.finish()
        }, 300)
    }
}

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Loading.activity = this

        lottie_ani.run {
            setAnimation("loading.json")
            loop(true)
            playAnimation()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}