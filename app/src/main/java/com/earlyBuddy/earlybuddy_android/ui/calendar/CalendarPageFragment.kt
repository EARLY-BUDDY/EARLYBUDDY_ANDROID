package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseFragment
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.databinding.FragmentCalendarPageBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemCalendarDateBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Date
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Schedule
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.util.NonScrollGridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarPageFragment : BaseFragment<FragmentCalendarPageBinding, CalendarViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_page

    override val viewModel : CalendarViewModel by viewModel()

    lateinit var calendarRecyclerViewAdapter: CalendarRecyclerViewAdapter
    var position = 0


    private var timeByMillis : Long = 0
    private val dataList : ArrayList<Date> by lazy { ArrayList<Date>()}

    val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
    val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
    val dateFormat = SimpleDateFormat("dd", Locale.KOREA)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel
        position = arguments!!.getInt("position", 0)

//        setLoading()
        setRv()
        setCalendar()
    }

    override fun onResume() {
        super.onResume()
        Log.e("hiroo", "hello")
        setCalendar()
    }

    private fun setRv(){

        calendarRecyclerViewAdapter = CalendarRecyclerViewAdapter()
        calendarRecyclerViewAdapter.apply{
            setOnItemClickListener(onCalendarRecyclerViewAdpater)
            setHasStableIds(true)
        }

        viewDataBinding.fragCalendarPageRv.run{
            adapter = calendarRecyclerViewAdapter
            layoutManager = NonScrollGridLayoutManager(activity!!, 7)
        }
    }

    val onCalendarRecyclerViewAdpater
            = object : CalendarRecyclerViewAdapter.OnItemClickListener{
        override fun onItemClicked(item: Date) {
            for(i in position - 3 .. position + 3) {
                (activity!! as CalendarActivity).calendarPagerAdapter.getItem(i).calendarRecyclerViewAdapter.removeClickDay()
            }

            (activity!! as CalendarActivity).showSchedule(viewModel.getScheduleByDay(item.year, item.month, item.date))

            calendarRecyclerViewAdapter.clickDay(item.month, item.date)
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

        viewModel.getSchedule(year, month)

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
        val lines = 6
        viewModel.schedule.observe(requireActivity(), androidx.lifecycle.Observer {
            makeSchedule(mCalendar, mPrevCalendar, mNextCalendar, mInstanceCalendar, date, lines)
        })



    }

    private fun makeSchedule(mCalendar: Calendar, mPrevCalendar: Calendar, mNextCalendar: Calendar,
                             mInstanceCal: Calendar, date : java.util.Date, lines: Int){


        val year = yearFormat.format(date)
        val month = monthFormat.format(date)
        val day = dateFormat.format(date)

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

            dataList.add(
                Date(
                    mPrevCalendar.get(Calendar.YEAR).toString(),
                    (mPrevCalendar.get(Calendar.MONTH) + 1).toString(),
                    i.toString(),
                    0,
                    false,
                    false,
                    false,
                    arrayListOf(),
                    lines
                )
            )

        }


        // 이번달
        for(i in 0 until mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {

            mInstanceCal.set(year.toInt(), month.toInt() - 1, i + 1)
            instanceDayNum = mInstanceCal.get(Calendar.DAY_OF_WEEK)

            val scheduleList = java.util.ArrayList<Schedule>()
            val instanceScheduleList = viewModel.getScheduleByMonth(year, month)
            for (j in instanceScheduleList.indices) {
                if (instanceScheduleList[j].scheduleStartTime.substring(8, 10).toInt() == i + 1) {
                    scheduleList.add(instanceScheduleList[j])
                }
            }

            if (i + 1 == today.toInt() && month == todayMonth && year == todayYear) {
                dataList.add(
                    Date(
                        year.toString(),
                        month.toString(),
                        (i + 1).toString(),
                        instanceDayNum,
                        true,
                        true,
                        true,
                        scheduleList,
                        lines
                    )
                )

                (activity!! as CalendarActivity).showSchedule(viewModel.getScheduleByDay(year, month, today))


            } else {
                dataList.add(
                    Date(
                        year.toString(),
                        month.toString(),
                        (i + 1).toString(),
                        instanceDayNum,
                        false,
                        true,
                        false,
                        scheduleList,
                        lines
                    )
                )
            }
        }

//        if(nextDayNum != 1){
            for(i in 1 until 16 - nextDayNum){

//                val scheduleList = viewModel.getSchedule(
//                    mNextCalendar.get(Calendar.YEAR).toString(),
//                    mNextCalendar.get(Calendar.MONTH).toString()
//                )

                dataList.add(
                    Date(
                        mNextCalendar.get(Calendar.YEAR).toString(),
                        (mNextCalendar.get(Calendar.MONTH) + 1).toString(),
                        i.toString(),
                        0,
                        false,
                        false,
                        false,
                        arrayListOf(),
                        lines
                    )
                )
            }

//        }

        refreshCal()
    }

    private fun refreshCal(){
        calendarRecyclerViewAdapter.run{
            replaceAll(dataList)
            notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setTimeByMillis(timeByMillis : Long){
        this.timeByMillis = timeByMillis
    }

    companion object {
        fun newInstance(position: Int): CalendarPageFragment {
            val frg = CalendarPageFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            frg.arguments = bundle
            return frg
        }
    }
}