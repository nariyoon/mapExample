package com.nryoon.mapexample.naver.data

import com.google.gson.JsonObject

data class NaverMapAddress(
    val roadAddress: String,
    val jibunAddress: String,
    val englishAddress: String,
    val x: String,
    val y: String,
    val distance: Double,
    val addressElement: JsonObject
)
