package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Station
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThorughRouteDetailBinding

class PathDetailAdapter(
    private val routeDetail: ArrayList<Station>,
    private val trafficType: Int,
    private val type: Int
) :
    RecyclerView.Adapter<RouteDetailViewHolder>() {
    override fun getItemCount(): Int {
        return routeDetail.size
    }

    override fun onBindViewHolder(holder: RouteDetailViewHolder, position: Int) {
        holder.bind(routeDetail[position], trafficType, type)
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

    fun bind(data: Station, trafficType: Int, type: Int) {
        detailBinding.stationName = data.stationName
        when (trafficType) {
            1 -> {  //지하철
                detailBinding.tints = TransportMap.subwayMap[type]!![0]
            }
            2 -> {  //버스
                detailBinding.tints = TransportMap.busMap[type]
            }
        }
    }
}