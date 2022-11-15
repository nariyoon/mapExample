package com.nryoon.mapexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.googleBtn).setOnClickListener(this::clickListener)
        findViewById<Button>(R.id.kakaoBtn).setOnClickListener(this::clickListener)
        findViewById<Button>(R.id.naverBtn).setOnClickListener(this::clickListener)
    }


    fun clickListener(view: View) {
        val targetActivity =
            when (view.id) {
                R.id.googleBtn -> GoogleMapActivity::class.java
                R.id.kakaoBtn -> KakaoMapActivity::class.java
                R.id.naverBtn -> NaverMapActivity::class.java
                else -> {
                    NaverMapActivity::class.java
                }
            }
        startActivity(Intent(this, targetActivity))
    }

}