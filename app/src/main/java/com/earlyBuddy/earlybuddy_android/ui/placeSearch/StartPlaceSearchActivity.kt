package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.databinding.ActivityStartPlaceSearchBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemRecentPlaceBinding
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_end_place_search.*
import kotlinx.android.synthetic.main.activity_start_place_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class StartPlaceSearchActivity : BaseActivity<ActivityStartPlaceSearchBinding, PlaceSearchViewModel>() {

    val bundle = Bundle(1)
    private val placeListFragment = PlaceListFragment()
    private val placeResultFragment = PlaceResultFragment()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : LocationCallback
    var latitude = 0.0
    var longitude = 0.0

    override val layoutResID: Int
        get() = R.layout.activity_start_place_search
    override val viewModel: PlaceSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewDataBinding.vm = viewModel

        textWatch()
        getLocationUpdates()
        setClick()
        setRv()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    fun setFrag(){
        val nowFrag = supportFragmentManager.findFragmentById(R.id.act_start_place_search_container)
        if(act_start_place_search_et_search.text.isEmpty() && nowFrag==placeListFragment){
            supportFragmentManager.beginTransaction()
                .remove(placeListFragment).commit()
        } else if(act_start_place_search_et_search.text.isNotEmpty() && nowFrag!=placeListFragment){
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.act_start_place_search_container,
                    placeListFragment
                ).commit()
            bundle.putInt("flag", 1)
            bundle.putDouble("longitude", longitude)
            bundle.putDouble("latitude", latitude)
            placeListFragment.arguments = bundle
        }
    }

    fun getPlaceData(){
        val keyword = act_start_place_search_et_search.text.toString()
        viewModel.getPlaceSearchData(keyword, longitude, latitude)
    }

    private fun textWatch(){
        act_start_place_search_et_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setFrag()
                if(act_start_place_search_et_search.text.isNotEmpty())
                    getPlaceData()
            }
        })
    }

    private fun setClick(){
        act_start_place_search_iv_cancel.setOnClickListener{
            act_start_place_search_et_search.text.clear()
            val nowFrag = supportFragmentManager.findFragmentById(R.id.act_start_place_search_container)
            if(nowFrag!=null) {
                supportFragmentManager.beginTransaction()
                    .remove(nowFrag).commit()
            }
            val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(act_start_place_search_et_search, 0)
            act_start_place_search_et_search.findFocus()
        }

        act_start_place_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_start_place_search_et_search.windowToken, 0)

                getPlaceData()
                viewModel.insert(RecentPlaceEntity(placeName =  act_start_place_search_et_search.text.toString()))
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_start_place_search_container,
                        placeResultFragment
                    ).commit()
                bundle.putInt("flag", 1)
                placeResultFragment.arguments = bundle
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setRv(){
        viewDataBinding.actStartPlaceSearchRv.apply {
            adapter =
                object : BaseRecyclerViewAdapter<RecentPlaceEntity, ItemRecentPlaceBinding>() {
                    override val bindingVariableId: Int
                        get() = BR.recentPlace
                    override val layoutResID: Int
                        get() = R.layout.item_recent_place
                    override val listener: OnItemClickListener?
                        get() = onClickListener
                }
            layoutManager = LinearLayoutManager(this@StartPlaceSearchActivity)
        }

        viewModel.places.observe(this, Observer {
            (viewDataBinding.actStartPlaceSearchRv.adapter as BaseRecyclerViewAdapter<RecentPlaceEntity, ItemRecentPlaceBinding>)
                .replaceAll(it)
            (viewDataBinding.actStartPlaceSearchRv.adapter as BaseRecyclerViewAdapter<RecentPlaceEntity, ItemRecentPlaceBinding>)
                .notifyDataSetChanged()
        })
    }

    val onClickListener
            = object : BaseRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClicked(item: Any?, position: Int?) {
            val recentPlace = (item as RecentPlaceEntity).placeName
            viewModel.getPlaceSearchData(recentPlace, longitude, latitude)
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.act_start_place_search_container,
                    placeResultFragment
                ).commit()
            bundle.putInt("flag", 1)
            placeResultFragment.arguments = bundle
        }
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
                    Log.e("location@@@@", location.latitude.toString())
                    Log.e("longitude@@@", location.longitude.toString())
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