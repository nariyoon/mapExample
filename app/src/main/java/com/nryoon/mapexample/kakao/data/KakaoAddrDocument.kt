package com.nryoon.mapexample.kakao.data

data class KakaoAddrDocument(
    val address_name: String,
    val address_type: String,
    val x: String, // longitude
    val y: String, // latitude
    val address: KakaoAddress,
    val road_address: KakaoRoadAddress
)
