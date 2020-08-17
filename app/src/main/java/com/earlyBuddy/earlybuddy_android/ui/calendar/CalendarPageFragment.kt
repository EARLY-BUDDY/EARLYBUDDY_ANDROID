package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import com.earlyBuddy.earlybuddy_android.databinding.FragmentCalendarPageBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemCalendarDateBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarPageFragment : BaseFragment<FragmentCalendarPageBinding, CalendarVIewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_page

    override val viewModel = CalendarVIewModel()

    private var timeByMillis : Long = 0
    private val dataList : ArrayList<Date> by lazy { ArrayList<Date>()}

    val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
    val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
    val dateFormat = SimpleDateFormat("dd", Locale.KOREA)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        setRv()
    }

    private fun setRv(){
        viewDataBinding.fragCalendarPageRv.run{
            adapter = object : BaseRecyclerViewAdapter<Date, ItemCalendarDateBinding>(){
                override val layoutResID: Int
                    get() = R.layout.item_calendar_date
                override val bindingVariableId: Int
                    get() = BR.date
                override val listener: OnItemClickListener?
                    get() = null
            }
        }

    }

    private fun setCalendar(){
        val mCalendar = Calendar.getInstance()
        val mPrevCalendar = Calendar.getInstance()
        val mNextCalendar = Calendar.getInstance()
        val mInstanceCalendar = Calendar.getInstance()

        // 날짜 세팅
        val date = Date(timeByMillis)

        // 현재 캘린더의 년, 월, 일
        val year = yearFormat.format(date)
        val month = monthFormat.format(date)
        val day = dateFormat.format(date)

        mCalendar.set(year.toInt(), month.toInt()-1, 1)
        when(month.toInt() - 1) {
            1 -> {
                mPrevCalendar.set(year.toInt(), 12, 1)
                mNextCalendar.set(year.toInt(), month.toInt(), 1)
            }
            12 -> {
                mPrevCalendar.set(year.toInt(), month.toInt() - 2, 1)
                mNextCalendar.set(year.toInt() + 1, 1, 1)
            }
            else -> {
                mPrevCalendar.set(year.toInt(), month.toInt() - 2, 1)
                mNextCalendar.set(year.toInt(), month.toInt(), 1)
            }
        }

        // 캘린더 줄 수 설정
        val lines: Int
        when(mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            28 -> {
                if(mCalendar.get(Calendar.DAY_OF_MONTH) == 1) lines = 4
                else lines = 5
            }
            30 -> {
                if(mCalendar.get(Calendar.DAY_OF_MONTH) == 7) lines = 6
                else lines = 5
            }
            31 -> {
                when(mCalendar.get(Calendar.DAY_OF_MONTH)){
                    6,7 -> lines = 6
                    else -> lines = 5
                }
            }
            else -> lines = 5
        }

        viewModel.schedule.observe(this, androidx.lifecycle.Observer {
            makeSchedule(mCalendar, mPrevCalendar, mNextCalendar, mInstanceCalendar, date, lines)
        })

    }

    private fun makeSchedule(mCalendar: Calendar, mPrevCalendar: Calendar, mNextCalendar: Calendar,
                             mInstanceCal: Calendar, date : Date, lines: Int){

        val todayYear = yearFormat.format(System.currentTimeMillis())
        val todayMonth = monthFormat.format(System.currentTimeMillis())
        val today = dateFormat.format(System.currentTimeMillis())

        dataList.clear()
        val dayNum = mCalendar.get(Calendar.DAY_OF_WEEK)
        val nextDayNum = mNextCalendar.get(Calendar.DAY_OF_WEEK)
        var instanceDayNum : Int

        // 저번달
        for(i in mPrevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayNum + 2
                until mPrevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1) {

            val scheduleList = viewModel.getSchedule(
                mPrevCalendar.get(Calendar.YEAR).toString(),
                mPrevCalendar.get(Calendar.MONTH).toString()
            )

        }
    }

}