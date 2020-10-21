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
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import com.earlyBuddy.earlybuddy_android.ui.Loading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home_path.*

class TestPathActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val searchRouteRepository: SearchRouteRepository =
        SearchRouteRepository(remoteDataSource = RemoteDataSourceImpl())
    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: PathAdapter

    private lateinit var pathData : Path
    private var startAdd = ""
    private var endAdd = ""


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_path)

        pathData = intent.getSerializableExtra("path") as Path
        startAdd = intent.getStringExtra("startAdd")
        endAdd = intent.getStringExtra("endAdd")
        Log.e("pathData", pathData.toString())

        routeRecyclerView = findViewById(R.id.path_rv)
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
    }

    fun setClick(){
        act_home_path_tv_btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("path", pathData)
            intent.putExtra("startAdd", startAdd)
            intent.putExtra("endAdd", endAdd)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}