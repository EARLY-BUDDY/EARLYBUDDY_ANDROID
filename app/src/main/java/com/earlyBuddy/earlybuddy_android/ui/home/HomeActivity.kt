package com.earlyBuddy.earlybuddy_android.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var random =( Math.random()*2).toInt()
        if(random ==1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.act_home_fl_home_fragment, BeforeDayFragment())
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.act_home_fl_home_fragment, NoPlanFragment())
                .commit()
        }
    }
}
