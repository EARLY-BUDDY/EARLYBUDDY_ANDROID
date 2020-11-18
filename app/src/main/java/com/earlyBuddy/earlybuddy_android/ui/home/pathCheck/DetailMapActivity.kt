package com.earlyBuddy.earlybuddy_android.ui.home.pathCheck

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_detail_map.*

class DetailMapActivity : AppCompatActivity(), OnMapReadyCallback {

    var x: Double? = null
    var y: Double? = null
    var address: String? = null
    var tints = ""
    var transNumber = ""
    var direction = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_map)

        x = intent.getDoubleExtra("x", 0.0)
        y = intent.getDoubleExtra("y", 0.0)
        tints = intent.getStringExtra("tints")
        transNumber = intent.getStringExtra("transNumber")
        direction = intent.getStringExtra("direction")

        address = intent.getStringExtra("address")

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.act_detail_map_frag) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.act_detail_map_frag, it).commit()
            }
        mapFragment.getMapAsync(this)

        setText()

        act_detail_map_iv_back.onlyOneClickListener {
            finish()
        }

    }


    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val location = LatLng(y!!, x!!)
        val marker = Marker()
        marker.position = location
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker)
        marker.captionTextSize = 15f
        marker.captionOffset = 20
        marker.map = naverMap
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(y!!, x!!))
        naverMap.moveCamera(cameraUpdate)
    }

    fun setText() {
        act_detail_map_tv_title.text = address
        act_detail_map_tv_address_title.text = address
        act_detail_map_tv_address_desc.text = direction +" 방면"

        act_detail_map_cl_address.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(tints))
        act_detail_map_tv_number.text = transNumber
    }
}