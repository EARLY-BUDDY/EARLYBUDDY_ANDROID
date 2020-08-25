package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPathBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPathBinding
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.EndPlaceSearchActivity
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.StartPlaceSearchActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_path.*
import org.koin.android.viewmodel.ext.android.viewModel

class PathActivity : BaseActivity<ActivityPathBinding, PathViewModel>() {

    val bundle = Bundle()
    private val REQUEST_CODE_START = 7777
    private val REQUEST_CODE_END = 8888
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var startPlaceName: String? = null
    private var endPlaceName: String? = null
    lateinit var pathResultFrag: Fragment
    var sx: Double = 0.0
    var sy: Double = 0.0
    var ex: Double = 0.0
    var ey: Double = 0.0
    var searchPathType: Int = 0
    var sortPathType: Int = 0
    var sFlag: Int = 0
    var eFlag: Int = 0

    override val layoutResID: Int
        get() = R.layout.activity_path
    override val viewModel: PathViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        setClick()
        setRv()
    }

    override fun onResume() {
        super.onResume()
        if (sFlag == 1 && eFlag == 1) {
            getRoute()

            viewModel.insert(
                RecentPathEntity(
                    startPlaceName = viewDataBinding.actPathTvStart.text.toString(),
                    endPlaceName = viewDataBinding.actPathTvEnd.text.toString(),
                    sx = sx, sy = sy, ex = ex, ey = ey
                )
            )

            pathResultFrag = PathResultFragment()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.act_path_fl,
                    pathResultFrag
                ).commit()
            bundle.putString("startAdd", viewDataBinding.actPathTvStart.text.toString())
            bundle.putString("endAdd", viewDataBinding.actPathTvEnd.text.toString())
            pathResultFrag.arguments = bundle
        }
    }

    private fun setRv() {
        viewDataBinding.actPathRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<RecentPathEntity, ItemRecentPathBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.recentPath
                    override val layoutResID: Int
                        get() = R.layout.item_recent_path
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(this@PathActivity)
        }

        viewModel.routes.observe(this, Observer {
            (viewDataBinding.actPathRv.adapter as BaseRecyclerViewAdapter<RecentPathEntity, ItemRecentPathBinding>)
                .replaceAll(it)
            (viewDataBinding.actPathRv.adapter as BaseRecyclerViewAdapter<RecentPathEntity, ItemRecentPathBinding>)
                .notifyDataSetChanged()
        })
    }

    val onClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {

            sx = (item as RecentPathEntity).sx
            sy = item.sy
            ex = item.ex
            ey = item.ey
            sFlag = 1
            eFlag = 1
            viewModel.getRouteData(sx, sy, ex, ey, 0)

            viewDataBinding.actPathTvStart.text =
                viewModel.routes.value!![position!!].startPlaceName
            viewDataBinding.actPathTvEnd.text = viewModel.routes.value!![position].endPlaceName
            viewDataBinding.actPathTvStart.setTextColor(resources.getColor(R.color.black))
            viewDataBinding.actPathTvEnd.setTextColor(resources.getColor(R.color.black))

            pathResultFrag = PathResultFragment()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.act_path_fl,
                    pathResultFrag
                ).commit()

            bundle.putString("startAdd", viewDataBinding.actPathTvStart.text.toString())
            bundle.putString("endAdd", viewDataBinding.actPathTvEnd.text.toString())
            pathResultFrag.arguments = bundle
        }
    }

    fun setClick() {
        viewDataBinding.actPathTvStart.setOnClickListener {
            val intent = Intent(this, StartPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_START)
        }
        viewDataBinding.actPathTvEnd.setOnClickListener {
            val intent = Intent(this, EndPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_END)
        }
        viewDataBinding.actPathIvCancel.setOnClickListener {
            act_path_tv_start.text = "출발지를 입력하세요"
            act_path_tv_end.text = "도착지를 입력하세요"
            act_path_tv_start.setTextColor(resources.getColor(R.color.mid_gray))
            act_path_tv_end.setTextColor(resources.getColor(R.color.mid_gray))
            sFlag = 0
            eFlag = 0

            for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment!!).commit()
            }
        }
        viewDataBinding.actPathIvChange.setOnClickListener {
            if (sFlag == 1 && eFlag == 1) {
                val temp = viewDataBinding.actPathTvStart.text
                viewDataBinding.actPathTvStart.text = viewDataBinding.actPathTvEnd.text
                viewDataBinding.actPathTvEnd.text = temp
                var tempD = sx
                sx = ex
                ex = tempD
                tempD = sy
                sy = ey
                ey = tempD
                getRoute()
            }
        }
        act_path_iv_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_START) {
                startPlaceName = data!!.getStringExtra("placeName")
                sx = data.getDoubleExtra("x", 0.0)
                sy = data.getDoubleExtra("y", 0.0)
                sFlag = data.getIntExtra("flag", 0)
                viewDataBinding.actPathTvStart.text = startPlaceName
                viewDataBinding.actPathTvStart.setTextColor(resources.getColor(R.color.black))

                Log.e("sFlag -> ", sFlag.toString())
                Log.e("sx좌표 -> ", sx.toString())
                Log.e("sy좌표 -> ", sy.toString())

            } else if (requestCode == REQUEST_CODE_END) {
                endPlaceName = data!!.getStringExtra("placeName")
                ex = data.getDoubleExtra("x", 0.0)
                ey = data.getDoubleExtra("y", 0.0)
                eFlag = data.getIntExtra("flag", 0)
                viewDataBinding.actPathTvEnd.text = endPlaceName
                viewDataBinding.actPathTvEnd.setTextColor(resources.getColor(R.color.black))

                Log.e("eFlag -> ", eFlag.toString())
                Log.e("ex좌표 -> ", ex.toString())
                Log.e("ey좌표 -> ", ey.toString())
            }
        }
    }

    fun getRoute() {
        Log.e("무엇이 문제인가", "${sx} + ${sy} + ${ex} + ${ey} + ${searchPathType}")
        viewModel.getRouteData(sx, sy, ex, ey, searchPathType)
    }

    fun sortRoute() {
        when (sortPathType) {
            1 // 최단 시간순
            -> viewModel.routeArrayList.sortedWith(compareBy {
                it.totalTime
            })
            2// 최소 환승순
            -> viewModel.routeArrayList.sortedWith(compareBy {
                it.transitCount
            })
            else // 최소 도보순
            -> viewModel.routeArrayList.sortedWith(compareBy {
                it.totalWalkTime
            })
        }

        viewModel._routeList.value = viewModel.routeArrayList
    }

    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("위치 서비스 사용")
        dialog.setMessage("얼리버디에서 위치 서비스를 사용하려면 \n설정으로 이동해 위치 서비스를 켜주세요!")
        dialog.setPositiveButton("설정") { d, whichButton ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        dialog.setNegativeButton("취소") { d, whichButton ->
            d.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getLastLocation() {
        Log.e("locationEnabled", isLocationEnabled().toString())
        Log.e("checkFinePermissions", checkPermissions().toString())
        if (!checkPermissions() && isLocationEnabled()) // 앱 수준 권한 부여 안됨
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
        else if (!isLocationEnabled() && checkPermissions()) // gps 사용을 허용 안함
            showAlertLocation()
        else if (!isLocationEnabled() && !checkPermissions()) { // 둘 다 허용 안함
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showAlertLocation()
                } else {
                    Toast.makeText(this, "위치 싀팔", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun checkPermissions(): Boolean { // 앱 수준 권한 부여 확인
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

//    private fun getLastLocation() {
//      Log.e("locationEnabled", isLocationEnabled().toString())
//      Log.e("checkFinePermissions", checkPermissions().toString())
//        if (checkPermissions())
//            if (!isLocationEnabled())
//                showAlertLocation()
//    }
//
//    private fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        )
//            return true
//        return false
//    }

    private fun isLocationEnabled(): Boolean { // gps 사용 확인
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
