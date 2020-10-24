package com.earlyBuddy.earlybuddy_android.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.initial.nickname.NickNameActivity
import com.earlyBuddy.earlybuddy_android.ui.initial.place.InitialPlaceActivity
import com.earlyBuddy.earlybuddy_android.ui.myPage.main.MyPageActivity
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.ScheduleDetailActivity
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.VerticalPathActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInActivity
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignUpActivity
import com.earlyBuddy.earlybuddy_android.ui.splash.SplashActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
//import com.google.firebase.FirebaseApp
//import com.google.firebase.iid.FirebaseInstanceId
//import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
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
            val intent = Intent(this, VerticalPathActivity::class.java)
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

        act_main_btn_schedule_detail.setOnClickListener {
            val intent = Intent(this, ScheduleDetailActivity::class.java)
            startActivity(intent)
        }
        act_main_btn_home_one.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("asd", 1)
            startActivity(intent)
        }
        act_main_btn_home_two.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("asd", 2)
            startActivity(intent)
        }
        act_main_btn_home_three.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("asd", 3)
            startActivity(intent)
        }

        act_main_btn_splash.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }


    }
}