package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SubPath
import com.earlyBuddy.earlybuddy_android.databinding.ItemPathMethodBinding

class PathMethodAdapter : RecyclerView.Adapter<PathMethodViewHolder>() {

    lateinit var data: ArrayList<SubPath>
    var totalLen : Int = 0
    var totalTime : Int = 0
    lateinit var binding: ItemPathMethodBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PathMethodViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_path_method,
            parent,
            false
        )
        return PathMethodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PathMethodViewHolder, position: Int) {
        if(position==0) holder.bind(totalLen, totalTime, data[position], data[position].trafficType,true)
        else holder.bind(totalLen, totalTime, data[position], data[position].trafficType, false)
    }

}

class PathMethodViewHolder(val binding: ItemPathMethodBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(totalLen : Int, totalTime : Int, data : SubPath, trafficType: Int, flag : Boolean){
        var methodLen = 0f
        if(data.sectionTime!=0) methodLen = totalLen * (data.sectionTime.toFloat() / totalTime.toFloat())
        Log.e("PathMethodViewHolder@@@@@@@@@ totalLen", totalLen.toString())
        Log.e("PathMethodViewHolder@@@@@@@@@ totalTime", totalTime.toString())
        Log.e("PathMethodViewHolder@@@@@@@@@ sectionTime", data.sectionTime.toString())
        Log.e("PathMethodViewHolder@@@@@@@@@ methodLen", methodLen.toString())
        binding.trafficType = data.trafficType
        if (flag && trafficType==3){
            binding.methodName = "${data.sectionTime}분"
            binding.itemPathMethodRl.layoutParams.width = methodLen.toInt()
        }else{
            when (trafficType) {
                1 -> {
                    binding.itemPathMethodRl.layoutParams.width = methodLen.toInt()
                    binding.methodColor = TransportMap.subwayMap[data.lane!!.type]!![0]
                    binding.methodName = TransportMap.subwayMap[data.lane.type]!![1]
                }
                2 -> {
                    binding.itemPathMethodRl.layoutParams.width = methodLen.toInt()
                    binding.methodColor = TransportMap.busMap[data.lane!!.type]
                    binding.methodName = data.lane.name
                }
                3 -> {
                    binding.itemPathMethodRl.layoutParams.width = methodLen.toInt()
                    binding.methodName = " "
                }
            }
        }
    }
}