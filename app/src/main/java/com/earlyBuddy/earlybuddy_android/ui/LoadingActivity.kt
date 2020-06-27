package com.earlyBuddy.earlybuddy_android.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import kotlinx.android.synthetic.main.activity_home_path.*

object Loading {
    lateinit var activity: Activity

    fun goLoading(nowActivityName: Context) {
        val intent = Intent(nowActivityName, LoadingActivity::class.java)
        nowActivityName.startActivity(intent)
    }

    fun exitLoading(){
        activity.finish()
    }
}

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Loading.activity = this

        lottie_ani.run {
            setAnimation("roading.json")
            loop(true)
            playAnimation()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}