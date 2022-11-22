package com.nryoon.mapexample.naver.data

data class NaverSearchResult(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverSearchResultItem>
)
