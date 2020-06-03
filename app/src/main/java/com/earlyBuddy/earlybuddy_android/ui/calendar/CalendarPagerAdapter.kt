package com.earlyBuddy.earlybuddy_android.ui.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CalendarPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val frgMap : HashMap<Int, CalendarPageFragment>
    private val monthListByMillis = ArrayList<Long>()
    private var numOfMonth = 0


    init {
        clearPrevFragment(fm)
        frgMap = HashMap()
    }

    override fun getCount(): Int {
       return monthListByMillis.size
    }

    override fun getItem(position: Int): CalendarPageFragment {
        var frg : CalendarPageFragment? = null
        frg = frgMap[position]!!
//        if(frgMap.size > 0) frg = frgMap[position]
//        if(frg == null) {
//            frg = CalendarPageFragment.newInstance(position)
//            frgMap[position] = frg
//        }
//
//        frg.setTimeByMillis(monthListByMillis[position])

        return frg


    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    private fun clearPrevFragment(fm: FragmentManager){
        val listFragment = fm.fragments

        if(listFragment != null){
            val _fm = fm.beginTransaction()

            for(f in listFragment){
                if(f is CalendarPageFragment) _fm.remove((f))
            }

            _fm.commitAllowingStateLoss()
        }
    }

    // 이개 무슨 함수지?
    fun setNumOfMonth(numOfMonth : Int){
        this.numOfMonth = numOfMonth

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -numOfMonth)
        calendar.set(Calendar.DATE, 1)

        for(i in 0 until numOfMonth * 2 + 1){
            monthListByMillis.add(calendar.timeInMillis)
            calendar.add(Calendar.MONTH, 1)
        }

        notifyDataSetChanged()
    }

    fun addPrev(){
        val lastMonthMillis = monthListByMillis[0]

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastMonthMillis
        calendar.set(Calendar.DATE, 1)

        for(i in numOfMonth downTo 1){
            monthListByMillis.add(0, calendar.timeInMillis)
            calendar.add(Calendar.MONTH, -1)
        }

        notifyDataSetChanged()
    }

    fun addNext(){
        val lastMonthMillis = monthListByMillis[monthListByMillis.size - 1]

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastMonthMillis

        for(i in 0 until numOfMonth){
            monthListByMillis.add(calendar.timeInMillis)
            calendar.add(Calendar.MONTH, 1)
        }

        notifyDataSetChanged()

    }

    fun getMonthDisplayed(position: Int) : String{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = monthListByMillis[position]

        val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
        val monthFormat = SimpleDateFormat("MM", Locale.KOREA)

        val date = Date()
        date.time = monthListByMillis[position]

        val year = yearFormat.format(date).toString()
        val month = monthFormat.format(date).toString()

        return year +"년 " + month +"월"

    }



}