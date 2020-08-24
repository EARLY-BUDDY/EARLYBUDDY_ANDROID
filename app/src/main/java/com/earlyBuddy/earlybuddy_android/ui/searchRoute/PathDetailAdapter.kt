package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Station
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThorughRouteDetailBinding

class PathDetailAdapter(
    private val routeDetail: ArrayList<String>,
    private val trafficType: Int,
    private val type: Int
) :
    RecyclerView.Adapter<RouteDetailViewHolder>() {
    override fun getItemCount(): Int {
        return routeDetail.size
    }

    override fun onBindViewHolder(holder: RouteDetailViewHolder, position: Int) {
        if (position == 0 || position == routeDetail.size - 1) {
            holder.bind(routeDetail[position], trafficType, type, true)
        } else {
            holder.bind(routeDetail[position], trafficType, type, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDetailViewHolder {
        val detailBinding: ItemPassThorughRouteDetailBinding =
            ItemPassThorughRouteDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RouteDetailViewHolder(detailBinding)
    }
}

class RouteDetailViewHolder(private val detailBinding: ItemPassThorughRouteDetailBinding) :
    RecyclerView.ViewHolder(detailBinding.root) {

    fun bind(data: String, trafficType: Int, type: Int, bool: Boolean) {
        if (bool) {
            detailBinding.itemClStopStationView.visibility = View.GONE
            detailBinding.itemClStopStationView.layoutParams = ViewGroup.MarginLayoutParams(0, 0)
            detailBinding.tints = "#FFFFFF"
            detailBinding.stationName = "아무거나"
            return
        }
        when (trafficType) {
            1 -> {  //지하철
                detailBinding.tints = TransportMap.subwayMap[type]!![0]
                detailBinding.stationName = "${data}역"
            }
            2 -> {  //버스
                detailBinding.tints = TransportMap.busMap[type]
                detailBinding.stationName = data
            }
        }
    }
}