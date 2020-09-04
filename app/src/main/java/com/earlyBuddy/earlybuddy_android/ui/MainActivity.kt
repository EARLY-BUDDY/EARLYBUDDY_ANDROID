package com.earlyBuddy.earlybuddy_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.initial.nickname.NickNameActivity
import com.earlyBuddy.earlybuddy_android.ui.initial.place.InitialPlaceActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.MyPageActivity
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleActivity
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.TestPathActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        TransportMap.jwt =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjEsImlhdCI6MTU5MjkyNzg3MiwiZXhwIjoxNjAwNzAzODcyfQ.FPFQZw_h2wtHx-ctS5U94bMDGe2PRTaFmkKADuOYYEE"
        act_main_btn_calendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_schedule_write.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_placeSearch.setOnClickListener {
            val intent = Intent(this, PathActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_signIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        act_main_btn_test.setOnClickListener {
            val intent = Intent(this, TestPathActivity::class.java)
            startActivity(intent)
        }
        act_main_btn_initial.setOnClickListener {
            val intent = Intent(this, NickNameActivity::class.java)
            startActivity(intent)
        }
        act_main_btn_initial_place.setOnClickListener {
            val intent = Intent(this, InitialPlaceActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_my_page.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}