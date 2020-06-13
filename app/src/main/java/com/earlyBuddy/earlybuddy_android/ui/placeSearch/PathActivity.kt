package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityPathBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_path.*

class PathActivity : BaseActivity<ActivityPathBinding, PathViewModel>() {

    private val REQUEST_CODE_START = 7777
    private val REQUEST_CODE_END = 8888
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var startPlaceName : String? = null
    private var endPlaceName : String? = null
    var sx : Double = 0.0
    var sy : Double = 0.0
    var ex : Double = 0.0
    var ey : Double = 0.0

    override val layoutResID: Int
        get() = R.layout.activity_path
    override lateinit var viewModel: PathViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PathViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        setClick()
    }

    fun setClick(){
        act_path_tv_start.setOnClickListener {
            val intent = Intent(this, StartPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_START)
        }
        act_path_tv_end.setOnClickListener {
            val intent = Intent(this, EndPlaceSearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_END)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_START){
                startPlaceName = data!!.getStringExtra("placeName")
                sx = data.getDoubleExtra("x", 0.0)
                sy = data.getDoubleExtra("y", 0.0)
                act_path_tv_start.text = startPlaceName
                act_path_tv_start.setTextColor(resources.getColor(R.color.black))
            }else if (requestCode == REQUEST_CODE_END){
                endPlaceName = data!!.getStringExtra("placeName")
                ex = data.getDoubleExtra("x", 0.0)
                ey = data.getDoubleExtra("y", 0.0)
                act_path_tv_end.text = endPlaceName
                act_path_tv_end.setTextColor(resources.getColor(R.color.black))
            }
        }
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
        if (checkPermissions())
            if (!isLocationEnabled())
                showAlertLocation()
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
