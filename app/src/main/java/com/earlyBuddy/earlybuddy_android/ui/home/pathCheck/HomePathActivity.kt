package com.earlyBuddy.earlybuddy_android.ui.home.pathCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import com.earlyBuddy.earlybuddy_android.databinding.ActivityHomePathBinding
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.PathAdapter
import com.earlyBuddy.earlybuddy_android.ui.searchRoute.RouteViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home_path.*

class HomePathActivity : BaseActivity<ActivityHomePathBinding, HomePathViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_home_path
    override val viewModel: HomePathViewModel = HomePathViewModel()
    private lateinit var routeAdapter: PathAdapter
    private val compositeDisposable = CompositeDisposable()
    private val searchRouteRepository: SearchRouteRepository =
        SearchRouteRepository(remoteDataSource = RemoteDataSourceImpl())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rottie_ani.setAnimation("roading.json")
        rottie_ani.loop(true)
        rottie_ani.playAnimation()

        connectRecyclerView()
        setRecyclerViewData()


    }

    private fun connectRecyclerView() {
        routeAdapter =
            PathAdapter("출발지역", "도착지역", object : RouteViewHolder.DropDownUpClickListener {
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

    private fun setRecyclerViewData() {
        compositeDisposable.add(
            searchRouteRepository.getSearchRouteData(
                126.994150735779,
                37.5613965840169,
                127.077858590612,
                37.6248693456496,
                0
            ).observeOn(AndroidSchedulers.mainThread())
                // 구독할 때 수행할 작업을 구현
                .doOnSubscribe {}
                // 스트림이 종료될 때 수행할 작업을 구현
                .doOnTerminate {

                    rottie_ani.clearAnimation()
                    rottie_ani.visibility = View.GONE
                }
                // 옵서버블을 구독
                .subscribe({
                    Log.e("getPlaceRes 응답 성공 : ", it.toString())
                    routeAdapter.setRouteItemList(it.data.path[2].subPath)
//                    routeAdapter.notifyDataSetChanged()
                }) {
                    Log.e("통신 실패 error : ", it.toString())
                })
    }
}
