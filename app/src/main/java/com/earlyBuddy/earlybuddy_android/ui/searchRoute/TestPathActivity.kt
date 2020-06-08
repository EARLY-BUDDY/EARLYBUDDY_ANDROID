package com.earlyBuddy.earlybuddy_android.ui.searchRoute

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TestPathActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val searchRouteRepository: SearchRouteRepository =
        SearchRouteRepository(remoteDataSource = RemoteDataSourceImpl())
    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: PathAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_path)
        routeRecyclerView = findViewById(R.id.path_rv)
        routeAdapter = PathAdapter("Asd", "ASd", object : RouteViewHolder.DropDownUpClickListener {
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
        routeRecyclerView.adapter = routeAdapter

        compositeDisposable.add(
            searchRouteRepository.getSearchRouteData(
                127.0773398361135,
                37.62492825094217,
                127.028000275071,
                37.4980854357918,
                0
            ).observeOn(AndroidSchedulers.mainThread())
                // 구독할 때 수행할 작업을 구현
                .doOnSubscribe {}
                // 스트림이 종료될 때 수행할 작업을 구현
                .doOnTerminate {}
                // 옵서버블을 구독
                .subscribe({
                    Log.e("getPlaceRes 응답 성공 : ", it.toString())
                    routeAdapter.setRouteItemList(it.data.path[0].subPath)
                    routeAdapter.notifyDataSetChanged()

                }) {
                    Log.e("통신 실패 error : ", it.toString())
                })

    }
}
