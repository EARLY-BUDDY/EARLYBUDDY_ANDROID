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
import kotlin.math.round

class PathMethodAdapter : RecyclerView.Adapter<PathMethodViewHolder>() {

    lateinit var data: ArrayList<SubPath>
    var totalLen : Int = 0
    var totalTime : Int = 0
    var minWalkLen : Int = 0
    var minTransLen : Int = 0
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PathMethodViewHolder, position: Int) {
        if(position==0) holder.bind(totalLen, totalTime,minWalkLen, minTransLen, data[position], data[position].trafficType,true)
        else holder.bind(totalLen, totalTime, minWalkLen, minTransLen, data[position], data[position].trafficType, false)
    }
}

class PathMethodViewHolder(val binding: ItemPathMethodBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(totalLen : Int, totalTime : Int, minWalkLen : Int, minTransLen : Int, data : SubPath, trafficType: Int, flag : Boolean){
        var methodLen = 0
        if(data.sectionTime!=0) methodLen = round(totalLen * (data.sectionTime.toFloat() / totalTime.toFloat())).toInt()

        binding.trafficType = data.trafficType

        if (flag){
            binding.methodName = "${data.sectionTime}분"
            binding.methodColor = "1"
            binding.itemPathMethodRl.layoutParams.width = methodLen + minWalkLen
        }
        else{
            if(trafficType == 3){
                if(data.sectionTime==0) {
                    binding.itemPathMethodRl.layoutParams.width = methodLen
                    binding.methodName = " "
                    binding.methodColor = "0"
                } else {
                    binding.itemPathMethodRl.layoutParams.width = methodLen + minWalkLen
                    binding.methodName = "${data.sectionTime}분"
                    binding.methodColor = "1"
                }
            }else{
                binding.itemPathMethodRl.layoutParams.width = methodLen + minTransLen

                data.lane?.run{
                    Log.e("data.lane.type", type.toString())
                    Log.e("data.lane.name", name.toString())

                }

                if(trafficType == 1){
                    binding.methodColor = TransportMap.subwayMap[data.lane!!.type]!![0]
                    binding.methodName = TransportMap.subwayMap[data.lane.type]!![1]
                } else {
                    binding.methodColor = TransportMap.busMap[data.lane!!.type]
                    binding.methodName = data.lane.name ?:  ""

                }
            }
        }
    }
}