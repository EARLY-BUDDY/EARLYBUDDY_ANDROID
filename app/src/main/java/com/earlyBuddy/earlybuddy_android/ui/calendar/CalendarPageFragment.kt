package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.os.Bundle
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
import com.earlyBuddy.earlybuddy_android.util.NonScrollGridLayoutManager

class CalendarPageFragment : BaseFragment<FragmentCalendarPageBinding, CalendarViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_calendar_page

    override val viewModel = CalendarViewModel()

    private var timeByMillis : Long = 0
    private val dataList : ArrayList<Date> by lazy { ArrayList<Date>()}

    val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
    val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
    val dateFormat = SimpleDateFormat("dd", Locale.KOREA)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel

        setRv()
        setCalendar()
    }

    private fun setRv(){
        viewDataBinding.fragCalendarPageRv.run{
            adapter = object : BaseRecyclerViewAdapter<Date, ItemCalendarDateBinding>(){
                override val layoutResID: Int
                    get() = R.layout.item_calendar_date
                override val bindingVariableId: Int
                    get() = BR.date
                override val listener: OnItemClickListener?
                    get() = onItemClickListener
            }
            layoutManager = NonScrollGridLayoutManager(activity!!, 7)
        }

    }

    val onItemClickListener
            = object :  BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {

//            val adapter = (viewDataBinding.fragCalendarPageRv.adapter as BaseRecyclerViewAdapter<Date, ItemCalendarDateBinding>)
//            val items = adapter.items
//            for (i in 0 until items.size) {
//                items[i].isClickDay = false
//            }
//
//            adapter.replaceAll(items)

            (item as Date).isClickDay = true
            viewDataBinding.fragCalendarPageRv.adapter?.notifyItemChanged(position!!)
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


        makeSchedule(mCalendar, mPrevCalendar, mNextCalendar, mInstanceCalendar, date, lines)


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

            val scheduleList = viewModel.getSchedule(
                mPrevCalendar.get(Calendar.YEAR).toString(),
                mPrevCalendar.get(Calendar.MONTH).toString()
            )

            dataList.add(
                Date(
                    mPrevCalendar.get(Calendar.YEAR).toString(),
                    (mPrevCalendar.get(Calendar.MONTH) + 1).toString(),
                    i.toString(),
                    0,
                    false,
                    false,
                    false,
                    scheduleList,
                    lines
                )
            )

        }

        // 이번달
        for(i in 0 until mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {

            mInstanceCal.set(year.toInt(), month.toInt() - 1, i + 1)
            instanceDayNum = mInstanceCal.get(Calendar.DAY_OF_WEEK)


            val scheduleList = java.util.ArrayList<Schedule>()
            val instanceScheduleList = viewModel.getSchedule(year, month)

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
                        false,
                        scheduleList,
                        lines
                    )
                )
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

        if(nextDayNum != 1){
            for(i in 1 until 9 - nextDayNum){

                val scheduleList = viewModel.getSchedule(
                    mNextCalendar.get(Calendar.YEAR).toString(),
                    mNextCalendar.get(Calendar.MONTH).toString()
                )

                dataList.add(
                    Date(
                        mNextCalendar.get(Calendar.YEAR).toString(),
                        (mNextCalendar.get(Calendar.MONTH) + 1).toString(),
                        i.toString(),
                        0,
                        false,
                        false,
                        false,
                        scheduleList,
                        lines
                    )
                )
            }
        }

        refreshCal()
    }

    private fun refreshCal(){
        (viewDataBinding.fragCalendarPageRv.adapter as BaseRecyclerViewAdapter<Any, *>)?.run{
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