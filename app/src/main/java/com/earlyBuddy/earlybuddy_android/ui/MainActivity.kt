package com.earlyBuddy.earlybuddy_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.ui.calendar.CalendarActivity
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.PlaceSearchActivity
import com.earlyBuddy.earlybuddy_android.ui.schedule.write.ScheduleActivity
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
            val intent = Intent(this, PlaceSearchActivity::class.java)
            startActivity(intent)
        }


    }
}