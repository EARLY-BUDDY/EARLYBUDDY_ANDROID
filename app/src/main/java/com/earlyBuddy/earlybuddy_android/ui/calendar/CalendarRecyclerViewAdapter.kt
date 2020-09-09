package com.earlyBuddy.earlybuddy_android.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Date
import com.earlyBuddy.earlybuddy_android.databinding.ItemCalendarDateBinding

class CalendarRecyclerViewAdapter : RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    val items = arrayListOf<Date>()

    fun replaceAll(items: ArrayList<Date>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
        }
    }

    fun clickDay(month: String, date : String){
        for(item in items){
            item.isClickDay = false

            if(item.month == month && item.date == date)
                item.isClickDay = true
        }

        notifyDataSetChanged()
    }

    fun removeClickDay(){
        for(item in items){
            item.isClickDay = false
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemCalendarDateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_calendar_date, parent, false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return (items[position].month.toLong() * 100) + (items[position].date.toLong())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.date = items[position]

        holder.dataBinding.root.setOnClickListener {
            if(items[position].isCurrentMonth)
                listener?.onItemClicked(items[position])
        }
    }

    inner class ViewHolder(val dataBinding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    interface OnItemClickListener {
        fun onItemClicked(item : Date)
    }

}
