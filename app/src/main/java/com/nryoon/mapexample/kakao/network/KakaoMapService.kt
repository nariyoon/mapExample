package com.nryoon.mapexample.kakao.network

import com.nryoon.mapexample.kakao.data.KakaoAddrSearchResult
import com.nryoon.mapexample.kakao.data.KakaoKeywordResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoMapService {
    @GET("/v2/local/search/keyword")
    suspend fun searchKeyword(
        @Header("Authorization") key: String,
        @Query("query") query: String,
    ): Response<KakaoKeywordResult>

    @GET("/v2/local/search/address")
    suspend fun searchAddress(
        @Header("Authorization") key: String,
        @Query("query") query: String,
    ): Response<KakaoAddrSearchResult>
}