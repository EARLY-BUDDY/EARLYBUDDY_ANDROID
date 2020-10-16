package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityScheduleBinding
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathActivity
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.EndPlaceSearchActivity
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.StartPlaceSearchActivity
import java.util.*

class ScheduleActivity : BaseActivity<ActivityScheduleBinding, ScheduleViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_schedule
    override lateinit var viewModel: ScheduleViewModel

    private val calendar = Calendar.getInstance()
    private var date : String = ""
    private var time : String = ""
    private val REQUEST_CODE_PATH = 7777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.activity = this

        setPickerClick()
        setClick()
    }

    fun setClick(){
        viewDataBinding.actScheduleClPlaceClick.setOnClickListener {
            val intent = Intent(this, PathActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PATH)
        }
        viewDataBinding.actScheduleTvRegister.setOnClickListener {
            val registFragment = ScheduleDialogFragment(1)

            registFragment.show(supportFragmentManager, "dialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CODE_PATH){

            }
        }
    }

    fun setPickerClick(){
        viewDataBinding.actScheduleTvDateClick.setOnClickListener {
            DatePickerDialog(this@ScheduleActivity, R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    calendar.run {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, monthOfYear)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    viewDataBinding.actScheduleTvDateClick.text = SimpleDateFormat("yyyy.MM.dd").format(calendar.time)
                    date = SimpleDateFormat("yyyy:MM:dd=").format(calendar.time)

                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        viewDataBinding.actScheduleTvTimeClick.setOnClickListener {
            TimePickerDialog(this@ScheduleActivity, R.style.MyTimePickerDialogTheme,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    calendar.run {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    }
                    viewDataBinding.actScheduleTvTimeClick.text = SimpleDateFormat("a hh:mm").format(calendar.time)
                    time = SimpleDateFormat("HH:mm:00").format(calendar.time)
                },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show()
            Log.e("date ", date+time)
        }
    }

    fun onWeekClick(view : View){
        Log.e("onWeekClick -> ", view.isSelected.toString())
        view.isSelected = !view.isSelected
    }
}
