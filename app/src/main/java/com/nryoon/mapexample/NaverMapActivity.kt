package com.nryoon.mapexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapView

class NaverMapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_map)

    }
}