package com.earlyBuddy.earlybuddy_android.ui.myPage.license

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import kotlinx.android.synthetic.main.activity_license.*
import kotlinx.android.synthetic.main.activity_top_title.view.*

class LicenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        val title = intent.getStringExtra("title")


        act_license_top.act_top_title_tv_title.text = title

        act_license_top.act_top_title_iv_back.onlyOneClickListener {
            finish()
        }
    }
}