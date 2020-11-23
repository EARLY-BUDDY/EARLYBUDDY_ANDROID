package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.databinding.ActivityScheduleBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathActivity
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathMethodAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_schedule.*
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.round

class ScheduleActivity : BaseActivity<ActivityScheduleBinding, ScheduleViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_schedule
    override val viewModel: ScheduleViewModel by viewModel()

    private val calendar = Calendar.getInstance()
    private var date: String = ""
    private var time: String = ""
    private var scheDate: String? = ""
    private var scheTime: String? = ""
    private var startAdd : String? = ""
    private var endAdd : String? = ""
    lateinit var pathData : Path
    private var noticeRange = 1
    private var noticeMin = 5
    private var weekdays = arrayListOf<Int>()
    private val REQUEST_CODE_PATH = 7777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.activity = this

        setPickerClick()
        setClick()
        setNotiSpinner()
        setNotiRangeSpinner()
        setObserve()
    }

    private fun setObserve() {
        viewModel.postSchedule.observe(this, androidx.lifecycle.Observer {
            if (it.status == 200) {
                val registFragment = ScheduleDialogFragment(it.data)
                registFragment.setOnDialogDismissedListener(onScheduleDialogFragmentDismissListener)
                registFragment.show(supportFragmentManager, "dialog")
            } else if (it.status == 400) {
                Toast.makeText(this, "탈 수 있는 차가 이미 지나갔어요ㅠㅠ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "일정 등록에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loading.observe(this, androidx.lifecycle.Observer {
            when (it) {
                true -> {
                    Loading.goLoading(this)
                }
                false -> {
                    Loading.exitLoading()
                }
            }
        })
    }

    private fun setClick() {
        viewDataBinding.actScheduleIvBackIcon.onlyOneClickListener {
            finish()
        }
        viewDataBinding.actScheduleEtName.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_schedule_et_name.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

        viewDataBinding.actScheduleClPlaceClick.onlyOneClickListener {
            if(scheDate.isNullOrEmpty() || scheTime.isNullOrEmpty()) Toast.makeText(this, "날짜와 도착시간을 먼저 설정해주세요!", Toast.LENGTH_SHORT).show()
            else {
                val today = Calendar.getInstance()
                val startDay = calendar
                Log.e("nowArr", today.toString())
                Log.e("startArr", startDay.toString())
                Log.e("compare", today.compareTo(startDay).toString())
                val startTime = time.substring(0,2).toInt()
                if(today.compareTo(startDay)>0){
                    Toast.makeText(this, "지난 날짜의 일정은 등록할 수 없어요", Toast.LENGTH_SHORT).show()
                }else if(startTime in 0..4){
                    Toast.makeText(this, "이 시간은 대중교통이 잠드는 시간이에요.", Toast.LENGTH_SHORT).show()
                }else {
                    val intent = Intent(this, PathActivity::class.java)
                    intent.putExtra("scheDate", scheDate)
                    intent.putExtra("scheTime", scheTime)
                    intent.putExtra("scheStart", "$date $time")
                    startActivityForResult(intent, REQUEST_CODE_PATH)
                }
            }

        }

        viewDataBinding.actScheduleTvRegister.onlyOneClickListener {
            if(viewDataBinding.actScheduleEtName.text.isNullOrEmpty() || startAdd.isNullOrEmpty() || endAdd.isNullOrEmpty())
                Toast.makeText(this, "빠진 정보가 있나 확인해주세요!", Toast.LENGTH_SHORT).show()
            else{
                postSche()

            }
        }

        viewDataBinding.actScheduleIvBackIcon.onlyOneClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PATH) {
                pathData = data!!.getSerializableExtra("path") as Path
                startAdd = data.getStringExtra("startAdd")
                endAdd = data.getStringExtra("endAdd")
                Log.e("scheAct", startAdd!! + endAdd + pathData)

                viewDataBinding.actSchedultTvRouteSelected.visibility = View.GONE
                viewDataBinding.actScheduleTvPlaceFromResult.apply {
                    text = startAdd
                    setTextColor(Color.parseColor("#3e3e3e"))
                }
                viewDataBinding.actScheduleTvPlaceToResult.apply {
                    text = endAdd
                    setTextColor(Color.parseColor("#3e3e3e"))
                }

                setRoute(pathData)
            }
        }
    }

    private fun setRoute(pathData: Path) {
        viewDataBinding.actSchduleTvAllocMethod.text = pathData.subPath[1].lane!!.name
        viewDataBinding.actScheduleTvAlloc.text = "약 ${pathData.subPath[1].lane!!.term.toString()}분"

        var totalTime = ""
        totalTime =
            if (pathData.totalTime < 60) {
                "${pathData.totalTime}분"
            } else {
                "${pathData.totalTime / 60}시간 ${pathData.totalTime % 60}분"
            }
        viewDataBinding.actScheduleRouteClRoute.actScheduleRouteTime.text = totalTime

        var pathType = ""
        when (pathData.pathType) {
            1 -> pathType = "지하철"
            2 -> pathType = "버스"
            3 -> pathType = "지하철 + 버스"
        }
        viewDataBinding.actScheduleRouteClRoute.actScheduleRouteTvMethod.text = pathType

        viewDataBinding.actScheduleRouteClRoute.actScheduleRouteRv.run {

            val pathMethodAdapter = PathMethodAdapter()

            val minWalkLen = round((width * 0.07))
            val minTransLen = round((width * 0.12))
            pathMethodAdapter.minWalkLen = minWalkLen.toInt()
            pathMethodAdapter.minTransLen = minTransLen.toInt()

            val width = width

            var cnt = 0
            for (item in pathData.subPath) {
                if (item.sectionTime == 0) cnt++
            }

            val transSize = pathData.subPath.size / 2
            val walkSize = transSize + 1 - cnt
            pathMethodAdapter.totalLen =
                width - (minWalkLen * walkSize).toInt() - (minTransLen * transSize).toInt()

            adapter = pathMethodAdapter

            pathMethodAdapter.data = pathData.subPath
            pathMethodAdapter.totalTime = pathData.totalTime
            pathMethodAdapter.notifyDataSetChanged()

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setPickerClick() {
        viewDataBinding.actScheduleTvDateClick.onlyOneClickListener {
            DatePickerDialog(this@ScheduleActivity,
                R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                    calendar.run {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, monthOfYear)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    date = SimpleDateFormat("yyyy.MM.dd").format(calendar.time)
                    viewDataBinding.actScheduleTvDateClick.text = date
                    scheDate = SimpleDateFormat("MM월 dd일 (EE)").format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        viewDataBinding.actScheduleTvTimeClick.onlyOneClickListener {
            TimePickerDialog(this@ScheduleActivity, R.style.MyTimePickerDialogTheme,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    calendar.run {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    }
                    scheTime = SimpleDateFormat("a hh:mm").format(calendar.time)
                    viewDataBinding.actScheduleTvTimeClick.text = scheTime
                    time = SimpleDateFormat("HH:mm:00").format(calendar.time)
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
            ).show()
            Log.e("date ", scheDate + scheTime)
        }
    }

    fun onWeekClick(view: View) {
        view.isSelected = !view.isSelected
    }

    private fun setNotiSpinner(){
        ArrayAdapter.createFromResource(this@ScheduleActivity, R.array.noti_array, android.R.layout.simple_spinner_item)
            .also {
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                viewDataBinding.actScheduleSpNoti.adapter = adapter
            }
        viewDataBinding.actScheduleSpNoti.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e("noti", parent!!.selectedItemPosition.toString())
                noticeMin =
                    when (parent!!.selectedItemPosition) {
                    0 -> 5
                    1 -> 10
                    2 -> 20
                    else -> 5
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setNotiRangeSpinner(){
        ArrayAdapter.createFromResource(this@ScheduleActivity, R.array.noti_range_array, android.R.layout.simple_spinner_item)
            .also {
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                viewDataBinding.actScheduleSpNotiRange.adapter = adapter
            }
        viewDataBinding.actScheduleSpNotiRange.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e("notiRange", parent!!.selectedItemPosition.toString())
                noticeRange =
                    when (parent!!.selectedItemPosition) {
                        0 -> 30
                        1 -> 60
                        2 -> 120
                        else -> 30
                    }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun postSche(){
        val jsonObject = JSONObject()
        var weekArray = JSONArray()

        val jsonString = Gson().toJson(pathData)
        val pathJson = JsonParser.parseString(jsonString) as JsonObject

        jsonObject.put("scheduleName", viewDataBinding.actScheduleEtName.text)
        jsonObject.put("scheduleStartTime", time)
        jsonObject.put("scheduleStartDay", date)
        jsonObject.put("startAddress", startAdd)
        jsonObject.put("startLongitude", 0)
        jsonObject.put("startLatitude", 0)
        jsonObject.put("endAddress", endAdd)
        jsonObject.put("endLongitude", 0)
        jsonObject.put("endLatitude", 0)
        jsonObject.put("noticeMin", noticeMin)
        jsonObject.put("noticeRange", noticeRange)

//        if (viewDataBinding.actScheduleIvMon.isSelected) weekdays.add(0)
//        if (viewDataBinding.actScheduleIvTue.isSelected) weekdays.add(1)
//        if (viewDataBinding.actScheduleIvWed.isSelected) weekdays.add(2)
//        if (viewDataBinding.actScheduleIvThu.isSelected) weekdays.add(3)
//        if (viewDataBinding.actScheduleIvFri.isSelected) weekdays.add(4)
//        if (viewDataBinding.actScheduleIvSat.isSelected) weekdays.add(5)
//        if (viewDataBinding.actScheduleIvSun.isSelected) weekdays.add(6)
        for(i in 0 until weekdays.size) weekArray.put(weekdays[i])
        jsonObject.put("weekdays", weekArray)

        val body = JsonParser.parseString(jsonObject.toString()) as JsonObject
        body.add("path", pathJson)
        Log.e("body", body.toString())
        viewModel.postScheData(body)
    }

    val onScheduleDialogFragmentDismissListener
            = object : ScheduleDialogFragment.OnDialogDismissedListener{
        override fun onDialogDismissed() {
            finish()
        }
    }
}