package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPathBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener

class RecentPathAdpater(private val context : Context,
                       private val clickListener : RecentPathViewHolder.onClickItemListener,
                       private val clickFavListener: RecentPathViewHolder.onClickDeleteListener) : RecyclerView.Adapter<RecentPathViewHolder>(){

    var data = mutableListOf<RecentPathEntity>()
    lateinit var binding : ItemRecentPathBinding

    fun replaceAll(array: List<RecentPathEntity>?) {
        array?.let {
            this.data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPathViewHolder {
        binding = ItemRecentPathBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentPathViewHolder(binding, clickListener, clickFavListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecentPathViewHolder, position: Int) {
        holder.binding.recentPath = data[position]
    }
}

class RecentPathViewHolder(val binding : ItemRecentPathBinding,
                          val clickListener : onClickItemListener,
                          val clickDeleteListener: onClickDeleteListener) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.itemRecentPathCl.onlyOneClickListener {
            clickListener.onClickStoreItem(adapterPosition,
                binding.recentPath!!)
        }
        binding.itemRecentPathIvDelete.onlyOneClickListener {
            clickDeleteListener.onDeleteItem(adapterPosition, binding.recentPath!!)
        }
    }

    interface onClickItemListener{
        fun onClickStoreItem(position: Int, item: RecentPathEntity)
    }

    interface onClickDeleteListener{
        fun onDeleteItem(position: Int, item: RecentPathEntity)
    }
}