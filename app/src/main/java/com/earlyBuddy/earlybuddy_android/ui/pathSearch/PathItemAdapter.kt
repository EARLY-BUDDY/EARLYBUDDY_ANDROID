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
import kotlin.math.round

class PathItemAdapter(
    private val clickListener: PathItemViewHolder.OnClickPathItemListener
) : RecyclerView.Adapter<PathItemViewHolder>() {

    private val data: ArrayList<Path> = ArrayList()
    lateinit var binding: ItemPathBinding

    fun clearAll(){
        data.clear()
        notifyDataSetChanged()
    }

    fun replaceAll(array: ArrayList<Path>?) {
        array?.let {
            this.data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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
        pathMethodAdapter.setHasStableIds(true)
    }

    interface OnClickPathItemListener {
        fun onClickPathItem(position: Int, pathIdx: Int)
    }

    fun bindAdapter(data : ArrayList<SubPath>, totalTime : Int) {
        binding.itemPathRv.post {
            binding.itemPathRv.adapter = pathMethodAdapter
            val width = binding.itemPathRv.width
//            Log.e("width ----++++++++", width.toString())

            val minWalkLen = round((width * 0.07))
            val minTransLen = round((width * 0.12))

//            Log.e("round%%%%%%%", "${minWalkLen} + ${minTransLen}")

            pathMethodAdapter.minWalkLen = minWalkLen.toInt()
            pathMethodAdapter.minTransLen = minTransLen.toInt()

            var cnt = 0
            for (item in data) {
                if (item.sectionTime==0) cnt++
            }

//            val totalSize = data.size
            val transSize = data.size/2
            val walkSize = transSize + 1 - cnt
            pathMethodAdapter.totalLen = width - (minWalkLen*walkSize).toInt() - (minTransLen*transSize).toInt()
        }
        pathMethodAdapter.data = data
        pathMethodAdapter.totalTime = totalTime
        pathMethodAdapter.notifyDataSetChanged()
    }

}
