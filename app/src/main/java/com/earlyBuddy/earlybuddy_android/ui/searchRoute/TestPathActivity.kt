package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.annotation.SuppressLint
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

class TestPathActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val searchRouteRepository: SearchRouteRepository =
        SearchRouteRepository(remoteDataSource = RemoteDataSourceImpl())
    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: PathAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_path)

        val pathData = intent.getSerializableExtra("path") as Path
        val startAdd = intent.getStringExtra("startAdd")
        val endAdd = intent.getStringExtra("endAdd")
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
        routeAdapter.subPathData = pathData.subPath
        routeRecyclerView.adapter = routeAdapter

//        compositeDisposable.add(
//            searchRouteRepository.getSearchRouteData(
//                126.994150735779,
//                37.5613965840169,
//                127.077858590612,
//                37.6248693456496,
//                0
//            ).observeOn(AndroidSchedulers.mainThread())
//                // 구독할 때 수행할 작업을 구현
//                .doOnSubscribe {}
//                // 스트림이 종료될 때 수행할 작업을 구현
//                .doOnTerminate {
//                    Loading.exitLoading()
//                }
//                // 옵서버블을 구독
//                .subscribe({
//                    Log.e("getPlaceRes 응답 성공 : ", it.toString())
//                    routeAdapter.setRouteItemList(it.data.path[0].subPath)
//
////                    routeAdapter.notifyDataSetChanged()
//                }) {
//                    Log.e("통신 실패 error : ", it.toString())
//                })
    }
}