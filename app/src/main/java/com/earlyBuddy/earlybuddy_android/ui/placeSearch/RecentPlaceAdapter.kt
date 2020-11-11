package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPlaceBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener

class RecentPlaceAdapter (private val clickItemListener : RecentPlaceViewHolder.onClickItemListener,
                          private val clickDeleteListener : RecentPlaceViewHolder.onClickDeleteListener) : RecyclerView.Adapter<RecentPlaceViewHolder>(){

    var data = mutableListOf<RecentPlaceEntity>()
    lateinit var binding: ItemRecentPlaceBinding

    fun replaceAll(array : List<RecentPlaceEntity>?){
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    fun removeAt(position: Int, size : Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, size)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPlaceViewHolder {
        binding = ItemRecentPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentPlaceViewHolder(binding, clickItemListener, clickDeleteListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecentPlaceViewHolder, position: Int) {
        holder.binding.recentPlace = data[position]
    }

}

class RecentPlaceViewHolder(val binding : ItemRecentPlaceBinding,
                           val clickListener : onClickItemListener,
                           val clickDeleteListener: onClickDeleteListener) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.itemRecentPlaceCl.onlyOneClickListener {
            clickListener.onClickItem(adapterPosition,
                binding.recentPlace!!)
        }
        binding.itemRecentPlaceIvDelete.onlyOneClickListener {
            clickDeleteListener.onDeleteItem(adapterPosition, binding.recentPlace!!)
        }
    }

    interface onClickItemListener{
        fun onClickItem(position: Int, item: RecentPlaceEntity)
    }

    interface onClickDeleteListener{
        fun onDeleteItem(position: Int, item: RecentPlaceEntity)
    }
}