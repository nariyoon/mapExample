package com.nryoon.mapexample.kakao.data

data class KakaoAddrSearchResult(
    val meta: KakaoMeta,
    val documents: List<KakaoAddrDocument>
)
