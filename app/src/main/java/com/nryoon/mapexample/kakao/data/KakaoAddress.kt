package com.nryoon.mapexample.kakao.data

data class KakaoAddress(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val region_3depth_h_name: String,
    val h_code: String,
    val b_code: String,
    val mountain_yn: String, // Y, N
    val main_address_no: String,
    val sub_address_no: String,
    val x: String, // longitude
    val y: String, // latitute
)
