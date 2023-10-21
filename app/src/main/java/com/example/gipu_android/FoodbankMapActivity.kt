package com.example.gipu_android

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.FoodbankMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.CircleOverlay

class FoodbankMapActivity:AppCompatActivity(), OnMapReadyCallback {
    private val binding by lazy{
        FoodbankMapBinding.inflate(layoutInflater)
    }

    lateinit var centerId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.foodbankMapback.setOnClickListener {
            finish()
        }
        binding.foodbankMapname.text = centerNameData.data[centerId]

        centerId = intent.getStringExtra("centerId")?: ""

        Log.d("센터이름", centerId)

        // 지도
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(centerMapData.data[centerId]!!.lat,centerMapData.data[centerId]!!.lng))
        naverMap.moveCamera(cameraUpdate)


        val circle = CircleOverlay()
        circle.center = LatLng(centerMapData.data[centerId]!!.lat,centerMapData.data[centerId]!!.lng)
        circle.radius = 30.0
        circle.map = naverMap
        circle.color = Color.parseColor("#5900A400")
    }
}