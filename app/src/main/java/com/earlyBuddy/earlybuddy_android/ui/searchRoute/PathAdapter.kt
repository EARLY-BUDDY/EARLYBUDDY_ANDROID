package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SubPath
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThroughRouteRidingBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThroughRouteWalkBinding
import java.util.*

class PathAdapter(
    private val startAddress: String,
    private val endAddress: String,
    private val clickListener: RouteViewHolder.DropDownUpClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   private val subPathData: ArrayList<SubPath> = ArrayList()
//    private val subPathData: ArrayList<SubPath> = pathData.subPath

    fun setRouteItemList(newSubPathData: List<SubPath>) {
        with(subPathData) {
            clear()
            addAll(newSubPathData)
        }
        notifyDataSetChanged()
    }

    //클릭 여부 확인
    fun getClicked(position: Int): Boolean? {
        return subPathData[position].clicked
    }

    fun setClicked(position: Int, clickStatus: Boolean) {
        subPathData[position].clicked = clickStatus
    }

    override fun getItemViewType(position: Int): Int {
        return subPathData[position].trafficType
    }

    fun getDistance(position: Int): Int {
        return subPathData[position].distance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            3 -> {
                val walkBinding: ItemPassThroughRouteWalkBinding =
                    ItemPassThroughRouteWalkBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return WalkViewHolder(startAddress, endAddress, subPathData, walkBinding)
            }
            else -> {
                val routeBinding: ItemPassThroughRouteRidingBinding =
                    ItemPassThroughRouteRidingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return RouteViewHolder(routeBinding, clickListener)
            }
        }

    }

    override fun getItemCount(): Int {
        return subPathData.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WalkViewHolder -> {
                holder.bind(subPathData[position], position)
            }
            is RouteViewHolder -> {
                holder.bindAdapter(
                        subPathData[position].passStopList,
                        holder.itemViewType,
                        subPathData[position].lane!!.type
                )
                holder.bind(subPathData[position])
            }
        }
    }
}

class RouteViewHolder(
    private val routeBinding: ItemPassThroughRouteRidingBinding,
    private val clickListener: DropDownUpClickListener
) :
    RecyclerView.ViewHolder(routeBinding.root) {

    private val pathDetailAdapter: PathDetailAdapter = PathDetailAdapter()

    init {
        routeBinding.itemPassRidingClDropDownUp.setOnClickListener {
            clickListener.dropDownUpClick(
                adapterPosition,
                routeBinding.itemPassRidingIvDropDownUpIcon,
                routeBinding.itemPassRidingRvRidingInfoDetail
            )
        }
    }

    interface DropDownUpClickListener {
        fun dropDownUpClick(
            position: Int,
            dropImageView: ImageView,
            detailRecyclerView: RecyclerView
        )
    }

    fun bind(data: SubPath) {
        if (data.distance == 0) {
            routeBinding.root.visibility = View.GONE
        }
        routeBinding.subPath = data
        routeBinding.subPathSectionTime = "약 " + data.sectionTime + "분"
        routeBinding.subPathStationCount = data.stationCount.toString() + "개 정류장"

        when (data.trafficType) {
            1 -> {  //지하철
                routeBinding.tints = TransportMap.subwayMap[data.lane!!.type]!![0]
                routeBinding.transNumber = TransportMap.subwayMap[data.lane.type]!![1]
                routeBinding.transImg =
                    routeBinding.root.context.resources.getDrawable(R.drawable.img_subway)
            }
            2 -> {  //버스
                routeBinding.tints = TransportMap.busMap[data.lane!!.type]
                routeBinding.transNumber = data.lane.name
                routeBinding.transImg =
                    routeBinding.root.context.resources.getDrawable(R.drawable.img_bus)
            }
        }
    }

    fun bindAdapter(routeDetail: ArrayList<String>, trafficType: Int, type: Int) {
        routeBinding.itemPassRidingRvRidingInfoDetail.adapter = pathDetailAdapter
        pathDetailAdapter.routeDetail = routeDetail
        pathDetailAdapter.trafficType = trafficType
        pathDetailAdapter.type = type
        pathDetailAdapter.notifyDataSetChanged()

        routeBinding.itemPassRidingRvRidingInfoDetail.layoutManager =
            LinearLayoutManager(routeBinding.root.context)
        routeBinding.itemPassRidingRvRidingInfoDetail.visibility = View.GONE
    }
}

class WalkViewHolder(
    private val startAddress: String,
    private val endAddress: String,
    private val subPathList: ArrayList<SubPath>,
    private val walkBinding: ItemPassThroughRouteWalkBinding
) :
    RecyclerView.ViewHolder(walkBinding.root) {

    fun bind(data: SubPath, position: Int) {
        walkBinding.subPath = data
        walkBinding.subPathSectionTime = "약 " + data.sectionTime + "분"
        walkBinding.subPathDistance = "도보 " +data.distance+ "m"

        when (position) {
            // 첫번째 걷기
            0 -> {
                walkBinding.fastInExitNo =
                    subPathList[position + 1].startExitNo
                walkBinding.actRouteTvWalkStartPoint.text = startAddress
                walkBinding.nextTrafficType = subPathList[position + 1].trafficType
                walkBinding.previousTrafficType = -1
                walkBinding.endPointName = subPathList[position + 1].startName
            }
            // 마지막 걷기
            subPathList.size - 1 -> {
                walkBinding.fastOutExitNo = subPathList[position - 1].endExitNo // 몇번출구로 나오기
                walkBinding.actRouteTvWalkEndPoint.text = endAddress
                walkBinding.previousTrafficType = subPathList[position - 1].trafficType
                walkBinding.nextTrafficType = -1
                walkBinding.startPointName = subPathList[position - 1].endName
            }
            // 중간 걷기
            else -> {
                if (subPathList[position].distance == 0) {
                    walkBinding.root.visibility = View.GONE
                    walkBinding.root.layoutParams = ViewGroup.MarginLayoutParams(0, 0)
                    return
                }
                walkBinding.fastOutExitNo = subPathList[position - 1].endExitNo // 몇번출구로 나오기
                walkBinding.fastInExitNo = subPathList[position + 1].startExitNo  // 몇번출구까지 걷기
                walkBinding.previousTrafficType = subPathList[position - 1].trafficType
                walkBinding.nextTrafficType = subPathList[position + 1].trafficType
                walkBinding.startPointName = subPathList[position - 1].endName
                walkBinding.endPointName = subPathList[position + 1].startName
            }
        }
    }

}