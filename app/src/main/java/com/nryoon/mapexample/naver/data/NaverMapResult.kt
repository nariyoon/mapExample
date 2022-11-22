package com.nryoon.mapexample.naver.data

data class NaverMapResult(
    val status: String,
    val errorMessage: String,
    val meta: NaverMapMeta,
    val addresses: List<NaverMapAddress>
)
