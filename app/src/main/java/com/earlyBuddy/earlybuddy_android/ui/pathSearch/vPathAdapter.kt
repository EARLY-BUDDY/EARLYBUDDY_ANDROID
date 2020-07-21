package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path

class vPathAdapter(private val context: Context) : RecyclerView.Adapter<vPathAdapter.vPathViewHolder>() {

    private val pathData: ArrayList<Path> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vPathViewHolder{
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_path, parent, false)
        return vPathViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pathData.size
    }

    override fun onBindViewHolder(holder: vPathViewHolder, position: Int) {
        holder.bind(pathData[position])
    }

    inner class vPathViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(data : Path){
            val hour = data.totalTime / 60
            val min = data.totalTime % 60

//            if(min != 0 && hour != 0){
//                time.text = String.format("%d시간 %d분", hour, min)
//            }else if(hour == 0){
//                time.text = String.format("%d분", min)
//            }else if(min == 0){
//                time.text = String.format("%d시간", hour)
//            }
//            transfer.text = String.format("환승 %d회", data.transitCount)
//            walk.text = String.format("도보 %d분", data.totalWalkTime)
//            money.text = String.format("%d원", data.totalPay)
        }
    }
}