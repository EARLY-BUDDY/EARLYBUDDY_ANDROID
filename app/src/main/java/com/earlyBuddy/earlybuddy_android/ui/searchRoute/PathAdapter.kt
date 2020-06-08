package com.earlyBuddy.earlybuddy_android.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SubPath

class PathAdapter(private val clickListener: RouteViewHolder.ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return subPathData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class RouteViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    interface ItemClickListener {
        fun dropDownClick(position: Int)
    }
}

class WalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}