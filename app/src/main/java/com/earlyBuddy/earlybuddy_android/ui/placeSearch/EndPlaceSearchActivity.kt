package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityEndPlaceSearchBinding
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_end_place_search.*

class EndPlaceSearchActivity : BaseActivity<ActivityEndPlaceSearchBinding, PlaceSearchViewModel>() {

    val bundle = Bundle(1)
    private val placeListFragment = PlaceListFragment()
    private val placeResultFragment = PlaceResultFragment()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : LocationCallback
    var latitude = 0.0
    var longitude = 0.0

    override val layoutResID: Int
        get() = R.layout.activity_end_place_search
    override lateinit var viewModel: PlaceSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this ,ViewModelProvider.NewInstanceFactory()).get(PlaceSearchViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        textWatch()
        getLocationUpdates()
        setClick()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


    fun getPlaceData(){
        val query = act_end_place_search_et_search.text.toString()
        viewModel.getPlaceSearchData(query, longitude, latitude)
    }

    fun setFrag(){
        val nowFrag = supportFragmentManager.findFragmentById(R.id.act_end_place_search_container)
        if(act_end_place_search_et_search.text.isEmpty() && nowFrag==placeListFragment){
            supportFragmentManager.beginTransaction()
                .remove(placeListFragment).commit()
        } else if(act_end_place_search_et_search.text.isNotEmpty() && nowFrag!=placeListFragment){
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.act_end_place_search_container,
                    placeListFragment
                ).commit()
            bundle.putInt("flag", 2)
            bundle.putDouble("longitude", longitude)
            bundle.putDouble("latitude", latitude)
            placeListFragment.arguments = bundle
        }
    }

    private fun textWatch(){
        act_end_place_search_et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setFrag()
                if(act_end_place_search_et_search.text.isNotEmpty())
                    getPlaceData()
            }
        })
    }

    private fun setClick(){
        act_end_place_search_iv_cancel.setOnClickListener{
            act_end_place_search_et_search.text.clear()
            val nowFrag = supportFragmentManager.findFragmentById(R.id.act_end_place_search_container)
            if(nowFrag!=null) {
                supportFragmentManager.beginTransaction()
                    .remove(nowFrag).commit()
            }
            val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(act_end_place_search_et_search, 0)
            act_end_place_search_et_search.findFocus()
        }

        act_end_place_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_end_place_search_et_search.windowToken, 0)

                getPlaceData()
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_end_place_search_container,
                        placeResultFragment
                    ).commit()
                bundle.putInt("flag", 2)
                placeResultFragment.arguments = bundle
                return@OnKeyListener true
            }
            false
        })
    }

    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
//                    Log.e("location@@@@", location.latitude.toString())
//                    Log.e("longitude@@@", location.longitude.toString())
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
