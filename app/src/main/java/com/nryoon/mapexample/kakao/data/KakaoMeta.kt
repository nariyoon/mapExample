package com.nryoon.mapexample.kakao.data

data class KakaoMeta (
    val is_end: Boolean,
    val total_count: Int,
    val pageable_count: Int,
    val same_name: RegionInfo? )