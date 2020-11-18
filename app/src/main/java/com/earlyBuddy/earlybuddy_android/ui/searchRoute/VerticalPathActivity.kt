package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.home.pathCheck.DetailMapActivity
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

        routeRecyclerView = findViewById(R.id.act_vertical_path_rv_path)
        routeAdapter =
            PathAdapter(startAdd!!, endAdd!!, object : RouteViewHolder.RouteClickListener {
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

                override fun mapClick(position: Int) {
                    val subPath = routeAdapter.getSubPath(position)
                    val x = subPath.startX
                    val y = subPath.startY
                    val startName = subPath.startName
                    var tints = ""
                    var transNumber = ""

                    val intent = Intent(this@VerticalPathActivity, DetailMapActivity::class.java)
                    intent.putExtra("x", x)
                    intent.putExtra("y", y)
                    intent.putExtra("address", startName)
                    when (subPath.trafficType) {
                        1 -> {
                            tints = TransportMap.subwayMap[subPath.lane!!.type]!![0]
                            transNumber = TransportMap.subwayMap[subPath.lane!!.type]!![1]
                        }
                        2 -> {
                            tints = TransportMap.busMap[subPath.lane!!.type]!!
                            transNumber = subPath.lane!!.name!!
                        }
                    }
                    intent.putExtra("tints", tints)
                    intent.putExtra("transNumber", transNumber)
                    intent.putExtra("direction", subPath.passStopList[1])
                    startActivity(intent)
                }
            })
        routeAdapter.setRouteItemList(pathData.subPath)
        routeRecyclerView.adapter = routeAdapter

        setClick()
        setText()
    }

    private fun setClick(){
        act_vertical_path_tv_btn.onlyOneClickListener {
            val intent = Intent()
            intent.putExtra("path", pathData)
            intent.putExtra("startAdd", startAdd)
            intent.putExtra("endAdd", endAdd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        act_vertical_path_iv_back.onlyOneClickListener {
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