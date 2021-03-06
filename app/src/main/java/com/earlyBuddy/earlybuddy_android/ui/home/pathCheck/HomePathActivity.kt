package com.earlyBuddy.earlybuddy_android.ui.home.pathCheck

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.TransportMap
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomePathBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.PathAdapter
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.RouteViewHolder
import kotlinx.android.synthetic.main.activity_home_path.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomePathActivity : BaseActivity<ActivityHomePathBinding, HomePathViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home_path
    override val viewModel: HomePathViewModel by viewModel()
    private lateinit var routeAdapter: PathAdapter

    lateinit var endAddress: String
    lateinit var startAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
        viewDataBinding.vm = viewModel

        val scheduleIdx = intent.getIntExtra("scheduleIdx", -1)
//        val fromHome = intent.getBooleanExtra("fromHome", false)

//        if (fromHome) {
//            viewDataBinding.actHomePathTvBtn.visibility = View.GONE
//        }
        viewModel.getPathData(scheduleIdx)

        viewDataBinding.actHomePathIvBack.onlyOneClickListener {
            finish()
        }

        connectRecyclerView()
        addObservedData()
    }

    private fun addObservedData() {
        viewModel.scheduleDetailResponse.observe(this, Observer {
            routeAdapter.setRouteItemList(it.data.path.subPath)
            routeAdapter.setStartEndAddress(
                it.data.scheduleInfo.startAddress,
                it.data.scheduleInfo.endAddress
            )
            Log.e("qweqw", it.data.path.subPath.toString())
        })

        viewModel.lottieVisible.observe(this, Observer {
            when (it) {
                true -> {
                    Loading.goLoading(this)
                }
                false -> {
                    Loading.exitLoading()
                }
            }
        })
    }

    private fun connectRecyclerView() {
        routeAdapter =
            PathAdapter("", "", object : RouteViewHolder.RouteClickListener {
                override fun dropDownUpClick(
                    position: Int,
                    dropImageView: ImageView,
                    detailRecyclerView: RecyclerView
                ) {
                    when (routeAdapter.getClicked(position)) {
                        true -> {
                            dropImageView.setImageResource(R.drawable.ic_dropbox_down)
                            detailRecyclerView.visibility = View.GONE
                            routeAdapter.setClicked(position, false)
                        }
                        else -> {
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

                    val intent = Intent(this@HomePathActivity, DetailMapActivity::class.java)
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
        act_home_path_rv_path.adapter = routeAdapter
    }

}
