package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_vertical_path.*

class VerticalPathActivity : AppCompatActivity() {
    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: PathAdapter

    private lateinit var pathData : Path
    private var startAdd = ""
    private var endAdd = ""
    private var scheTime = ""

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_path)

        pathData = intent.getSerializableExtra("path") as Path
        startAdd = intent.getStringExtra("startAdd")!!
        endAdd = intent.getStringExtra("endAdd")!!
        scheTime = intent.getStringExtra("scheTime")!!
//        Log.e("pathData", pathData.toString())

        routeRecyclerView = findViewById(R.id.act_vertical_path_rv_path)
        routeAdapter =
            PathAdapter(startAdd!!, endAdd!!, object : RouteViewHolder.DropDownUpClickListener {
                override fun dropDownUpClick(
                    position: Int,
                    dropImageView: ImageView,
                    detailRecyclerView: RecyclerView
                ) {
                    when (routeAdapter.getClicked(position)) {
                        true -> {
                            Log.e("리스트 접음", "~~")
                            dropImageView.setImageResource(R.drawable.ic_dropbox_down)
                            detailRecyclerView.visibility = View.GONE
                            routeAdapter.setClicked(position, false)
                        }
                        else -> {
                            Log.e("리스트 펼침", "~~")
                            dropImageView.setImageResource(R.drawable.ic_dropbox_up)
                            detailRecyclerView.visibility = View.VISIBLE
                            routeAdapter.setClicked(position, true)
                        }
                    }
                }
            })
        routeAdapter.setRouteItemList(pathData.subPath)
        routeRecyclerView.adapter = routeAdapter

        setClick()
        setText()
    }

    private fun setClick(){
        act_vertical_path_tv_btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("path", pathData)
            intent.putExtra("startAdd", startAdd)
            intent.putExtra("endAdd", endAdd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setText(){
        var totalTime = ""
        totalTime =
            if (pathData.totalTime < 60) {
                "${pathData.totalTime}분"
            } else {
                "${pathData.totalTime / 60}시간 ${pathData.totalTime % 60}분"
            }
        act_vertical_path_tv_hours.text = totalTime

        var pathType = ""
        when (pathData.pathType) {
            1 -> pathType = "지하철"
            2 -> pathType = "버스"
            3 -> pathType = "지하철 + 버스"
        }
        act_vertical_path_tv_trafficType.text = pathType
        act_vertical_path_tv_end_address.text = endAdd
        act_vertical_path_tv_arrive_time.text = "${scheTime} 까지"
    }
}