package com.earlyBuddy.earlybuddy_android.ui.initial.finish

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.databinding.ActivityFinishBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.home.HomeActivity

class FinishActivity : AppCompatActivity() {
    lateinit var viewDataBinding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_finish)
        viewDataBinding.lifecycleOwner = this@FinishActivity


        viewDataBinding.actFinishTvRegister.onlyOneClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}