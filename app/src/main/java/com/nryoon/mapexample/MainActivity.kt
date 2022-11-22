package com.nryoon.mapexample

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.nryoon.mapexample.kakao.KakaoMapActivity
import com.nryoon.mapexample.naver.NaverMapActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        findViewById<LinearLayout>(R.id.googleBtn).setOnClickListener(this::onClick)
        findViewById<LinearLayout>(R.id.kakaoBtn).setOnClickListener(this::onClick)
        findViewById<LinearLayout>(R.id.naverBtn).setOnClickListener(this::onClick)
        getHashKey()
    }

    private fun onClick(view: View) {
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

    private fun getHashKey(){
        var packageInfo : PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures){
            try{
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch(e: NoSuchAlgorithmException){
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }
}