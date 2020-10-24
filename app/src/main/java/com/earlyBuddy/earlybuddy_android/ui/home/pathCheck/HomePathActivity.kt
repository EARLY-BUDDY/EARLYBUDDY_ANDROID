package com.earlyBuddy.earlybuddy_android.ui.home.pathCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
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

        val scheduleIdx = intent.getIntExtra("scheduleIdx", -1)
        val scheduleName = intent.getStringExtra("scheduleName")
        val startTime = intent.getStringExtra("startTime")
        val pathType = intent.getIntExtra("pathType", -1)
        val totalTime = intent.getIntExtra("totalTime", -1)
        endAddress = intent.getStringExtra("endAddress")
        startAddress = intent.getStringExtra("startAddress")
        val fromHome = intent.getBooleanExtra("fromHome", false)

        if (fromHome) {
            viewDataBinding.actHomePathTvBtn.visibility = View.GONE
        }

        when (pathType) {
            0 -> viewDataBinding.actHomePathTvTrafficType.text = "버스 + 지하철"
            1 -> viewDataBinding.actHomePathTvTrafficType.text = "지하철"
            2 -> viewDataBinding.actHomePathTvTrafficType.text = "버스"
        }

        val totalTimeHour = totalTime / 60
        val totalTimeMinute = totalTime % 60
        if (totalTimeHour == 0) {
            viewDataBinding.actHomePathTvHours.text =
                "${totalTimeMinute}분"
        } else {
            viewDataBinding.actHomePathTvHours.text =
                "${totalTimeHour}시간 ${totalTimeMinute}분"
        }

        viewModel.getPathData(scheduleIdx)

        viewDataBinding.actHomePathTvTitle.text = scheduleName
        viewDataBinding.actHomePathTvStartTime.text = startTime + " 까지"
        viewDataBinding.actHomePathTvEndAddress.text = endAddress

        viewDataBinding.actHomePathIvBack.onlyOneClickListener {
            finish()
        }

        connectRecyclerView()
        addObservedData()
    }

    private fun addObservedData() {
        viewModel.scheduleDetailResponse.observe(this, Observer {
            routeAdapter.setRouteItemList(it.data.path.subPath)
            Log.e("qweqw",it.data.path.subPath.toString())
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
            PathAdapter(startAddress, endAddress, object : RouteViewHolder.DropDownUpClickListener {
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
            })
        act_home_path_rv_path.adapter = routeAdapter
    }

}
