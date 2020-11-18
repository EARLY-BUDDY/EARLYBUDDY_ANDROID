package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.earlyBuddy.earlybuddy_android.BR
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.base.BaseActivity
import com.earlyBuddy.earlybuddy_android.base.BaseRecyclerViewAdapter
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Favorite
import com.earlyBuddy.earlybuddy_android.databinding.ActivityStartPlaceSearchBinding
import com.earlyBuddy.earlybuddy_android.databinding.ItemFavPlaceBinding
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_start_place_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class StartPlaceSearchActivity : BaseActivity<ActivityStartPlaceSearchBinding, PlaceSearchViewModel>() {
    companion object{
        var getByInitial = -1
        var favoriteCategory = -1
    }

    val bundle = Bundle(1)
    private val placeListFragment = PlaceListFragment()
    private val placeResultFragment = PlaceResultFragment()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : LocationCallback
    var latitude = 0.0
    var longitude = 0.0
    var recentPlaceClick = 0

    override val layoutResID: Int
        get() = R.layout.activity_start_place_search
    override val viewModel: PlaceSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewDataBinding.vm = viewModel

        isLoadedByInitial()
        textWatch()
        getLocationUpdates()
        setClick()
        setFavPlaceRv()
        setRecentPlaceRv()
    }

    private fun isLoadedByInitial() {
        val intent = intent
        getByInitial = intent.getIntExtra("initial", -1)
        favoriteCategory = intent.getIntExtra("favoriteCategory",-1)
        if (getByInitial == 1) {
            // 1이면 최초가입에서 불러진 뷰
            viewDataBinding.actStartPlaceSearchViewLine.visibility = View.GONE
            viewDataBinding.actStartPlaceSearchRvFav.visibility = View.GONE
            viewDataBinding.actStartPlaceSearchTvTitle.text = "자주 가는 장소"
            viewDataBinding.actStartPlaceSearchEtSearch.hint = "장소를 입력해주세요"
        }
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
        if(recentPlaceClick==1) return

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
        act_start_place_search_iv_cancel.onlyOneClickListener{
            act_start_place_search_et_search.text.clear()

            val nowFrag = supportFragmentManager.findFragmentById(R.id.act_start_place_search_container)
            if(nowFrag!=null) {
                supportFragmentManager.beginTransaction()
                    .remove(nowFrag).commit()
            }
            act_start_place_search_et_search.findFocus()
        }

        act_start_place_search_iv_back.onlyOneClickListener {
            finish()
        }

        act_start_place_search_et_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_start_place_search_et_search.windowToken, 0)

                getPlaceData()

                if(viewDataBinding.actStartPlaceSearchEtSearch.text.isNotEmpty()){
                    viewModel.insert(RecentPlaceEntity(placeName = viewDataBinding.actStartPlaceSearchEtSearch.text.toString()))
                }

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

    private fun setFavPlaceRv(){
        viewDataBinding.actStartPlaceSearchRvFav.run{
            adapter = object : BaseRecyclerViewAdapter<Favorite, ItemFavPlaceBinding>(){
                override val bindingVariableId: Int
                    get() = BR.favPlace
                override val layoutResID: Int
                    get() = R.layout.item_fav_place
                override val listener: OnItemClickListener?
                    get() = favItemClick
            }
        }

        viewModel.getFavoritePlaceData()
        viewModel.favoritePlaceList.observe(this, Observer{
            (viewDataBinding.actStartPlaceSearchRvFav.adapter as BaseRecyclerViewAdapter<Favorite, *>).apply {
                replaceAll(it)
                notifyDataSetChanged()
            }
        })
    }

    private val favItemClick = object : BaseRecyclerViewAdapter.OnItemClickListener{
        override fun onItemClicked(item: Any?, position: Int?) {
            val intent = Intent()
            intent.putExtra("placeName", viewModel.favoritePlaceList.value!![position!!].favoriteInfo)
            intent.putExtra("x", viewModel.favoritePlaceList.value!![position].favoriteLongitude)
            intent.putExtra("y", viewModel.favoritePlaceList.value!![position].favoriteLatitude)
            intent.putExtra("flag", 1)
            intent.putExtra("favoriteCategory", -1)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setRecentPlaceRv(){

        val recentPlaceAdapter = RecentPlaceAdapter(object : RecentPlaceViewHolder.onClickItemListener{
            override fun onClickItem(position: Int, item: RecentPlaceEntity) {
                val recentPlace = item.placeName
                recentPlaceClick = 1
                viewDataBinding.actStartPlaceSearchEtSearch.clearFocus()

                viewModel.getPlaceSearchData(recentPlace, longitude, latitude)
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.act_start_place_search_container,
                        placeResultFragment
                    ).commit()
                bundle.putInt("flag", 1)
                placeResultFragment.arguments = bundle

                viewDataBinding.actStartPlaceSearchEtSearch.setText(viewModel.places.value!![position!!].placeName)
            }
        }, object :  RecentPlaceViewHolder.onClickDeleteListener {
            override fun onDeleteItem(position: Int, item: RecentPlaceEntity) {
                viewModel.delete(item)
            }
        })

        recentPlaceAdapter.setHasStableIds(true)

        viewDataBinding.actStartPlaceSearchRv.apply {
            adapter = recentPlaceAdapter
            layoutManager = LinearLayoutManager(this@StartPlaceSearchActivity)
        }

        viewModel.places.observe(this, Observer {
            recentPlaceAdapter.run {
                replaceAll(it)
                notifyDataSetChanged()
            }
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