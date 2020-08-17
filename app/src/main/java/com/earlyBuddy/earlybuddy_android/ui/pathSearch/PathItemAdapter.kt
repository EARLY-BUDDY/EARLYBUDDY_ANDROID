package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SubPath
import com.earlyBuddy.earlybuddy_android.databinding.ItemPathBinding

class PathItemAdapter(
    private val clickListener: PathItemViewHolder.OnClickPathItemListener
) : RecyclerView.Adapter<PathItemViewHolder>() {

    private val data: ArrayList<Path> = ArrayList()
    lateinit var binding: ItemPathBinding

    fun replaceAll(array: ArrayList<Path>?) {
        array?.let {
            this.data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PathItemViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_path,
            parent,
            false
        )
        return PathItemViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PathItemViewHolder, position: Int) {
        holder.binding.pathRes = data[position]
        holder.bindAdapter(data[position].subPath, data[position].totalTime)
    }
}

class PathItemViewHolder(
    val binding: ItemPathBinding,
    private val clickListener: OnClickPathItemListener
) : RecyclerView.ViewHolder(binding.root) {

    val size : RecyclerView = binding.itemPathRv
    var width : Int = 0
    private val pathMethodAdapter : PathMethodAdapter

    init {
        binding.itemPathCl.setOnClickListener {
            clickListener.onClickPathItem(adapterPosition, 1)
        }
        pathMethodAdapter = PathMethodAdapter()
    }

    interface OnClickPathItemListener {
        fun onClickPathItem(position: Int, pathIdx: Int)
    }

    fun bindAdapter(data : ArrayList<SubPath>, totalTime : Int) {
        binding.itemPathRv.post {
            binding.itemPathRv.adapter = pathMethodAdapter
            pathMethodAdapter.totalLen = binding.itemPathRv.width
        }
        pathMethodAdapter.data = data
        pathMethodAdapter.totalTime = totalTime
        pathMethodAdapter.notifyDataSetChanged()
    }
}
