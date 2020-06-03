package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPathBinding
import kotlinx.android.synthetic.main.activity_path.*

class PathActivity : BaseActivity<ActivityPathBinding, PathViewModel>() {

    private val REQUEST_CODE_START = 7777
    private val REQUEST_CODE_END = 8888

    override val layoutResID: Int
        get() = R.layout.activity_path
    override val viewModel: PathViewModel = PathViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)

        act_path_tv_start.setOnClickListener {
            val intent = Intent(this, StartPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_START)
        }
        act_path_tv_end.setOnClickListener {
            val intent = Intent(this, EndPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_END)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_START){
            if(resultCode == Activity.RESULT_OK){
                val startPlaceName = data!!.getStringExtra("startPlaceName")
                act_path_tv_start.setText(startPlaceName)
            }
        } else if(requestCode == REQUEST_CODE_END){
            if(resultCode == Activity.RESULT_OK){
                val endPlaceName = data!!.getStringExtra("endPlaceName")
                act_path_tv_end.setText(endPlaceName)
            }
        }

    }

}
