package com.nryoon.mapexample.kakao.data

data class KakaoRoadAddress(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val road_name: String,
    val underground_yn: String,
    val main_building_no: String,
    val sub_building_no: String,
    val building_name: String,
    val zone_no: String,
    val x: String, // longitude
    val y: String, // latitute
)
