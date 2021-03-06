package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.earlyBuddy.earlybuddy_android.R
import com.earlyBuddy.earlybuddy_android.onlyOneClickListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    var flag : Int? = null
    var x : Double? = null
    var y : Double? = null
    var placeName : String? = null
    var address : String? = null
    var roadAddress : String? = null
    var category: String? = null
    var favoriteCategory: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (intent.getIntExtra("initial", 0) == 1) {
            act_map_tv_title.text = "자주 가는 장소 선택"
            act_map_tv_flag.text = "선택"
            favoriteCategory = intent.getIntExtra("favoriteCategory", -1)
        }
        flag = intent.getIntExtra("flag", 0)
        x = intent.getDoubleExtra("x", 0.0)
        y = intent.getDoubleExtra("y", 0.0)
        val fx = x
        val fy = y
        placeName = intent.getStringExtra("placeName")
        address = intent.getStringExtra("address")
        roadAddress = intent.getStringExtra("roadAddress")
        category = intent.getStringExtra("category")

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.act_map_frag) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.act_map_frag, it).commit()
            }
        mapFragment.getMapAsync(this)

        setText()

        act_map_iv_cancel.onlyOneClickListener{
            finish()
        }
        act_map_iv_back.onlyOneClickListener {
            finish()
        }

        act_map_ll_choice.onlyOneClickListener{
            val intent = Intent()
            intent.putExtra("placeName", placeName)
            intent.putExtra("x", fx)
            intent.putExtra("y", fy)
            intent.putExtra("flag", 1)
            intent.putExtra("favoriteCategory", favoriteCategory)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun isLoadedByInitial() {

    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val location = LatLng(y!!, x!!)
        val marker = Marker()
        marker.position = location
//        if(placeName==null){
//            marker.captionText =  address!!
//        } else {
//            marker.captionText = placeName!!
//        }
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker)
        marker.captionTextSize = 15f
        marker.captionOffset = 20
        marker.map = naverMap
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(y!!, x!!))
        naverMap.moveCamera(cameraUpdate)
    }

    fun setText(){
        act_map_tv_search.text = placeName
        act_map_tv_name.text = placeName
        if(roadAddress.isNullOrEmpty()) act_map_tv_address.text = address
        else act_map_tv_address.text = roadAddress
        act_map_tv_category.text = category

        if(flag==2){
            act_map_tv_flag.text = "도착"
            act_map_tv_title.text = "도착지 선택"
        }
    }
}
