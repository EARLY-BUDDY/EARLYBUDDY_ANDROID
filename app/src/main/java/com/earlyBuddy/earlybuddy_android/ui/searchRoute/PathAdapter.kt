package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SubPath
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThroughRouteRidingBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemPassThroughRouteWalkBinding

class PathAdapter(
    private val startAddress: String,
    private val endAddress: String,
    private val clickListener: RouteViewHolder.DropDownUpClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var pathDetailAdapter: PathDetailAdapter
    private val subPathData: ArrayList<SubPath> = ArrayList()

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

    private fun getPreviousTrafficType(position: Int): Int {
        return subPathData[position - 1].trafficType
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
                return WalkViewHolder(startAddress, endAddress, walkBinding)
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
                holder.bind(subPathData[position])

            }
            is RouteViewHolder -> {

                pathDetailAdapter = PathDetailAdapter(
                    subPathData[position].passStopList!!.stations,
                    holder.itemViewType,
                    subPathData[position].lane!!.type
                )
                holder.bindAdapter(pathDetailAdapter)
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
        routeBinding.subPath = data
    }

    fun bindAdapter(detailAdapter: PathDetailAdapter) {
        routeBinding.itemPassRidingRvRidingInfoDetail.adapter = detailAdapter
        routeBinding.itemPassRidingRvRidingInfoDetail.layoutManager =
            LinearLayoutManager(routeBinding.root.context)
        routeBinding.itemPassRidingRvRidingInfoDetail.visibility = View.GONE
    }
}

class WalkViewHolder(
    private val startAddress: String,
    private val endAddress: String,
    private val walkBinding: ItemPassThroughRouteWalkBinding
) :
    RecyclerView.ViewHolder(walkBinding.root) {

    fun bind(data: SubPath) {
        walkBinding.subPath = data
    }

}