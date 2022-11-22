package com.nryoon.mapexample.naver.network

import com.nryoon.mapexample.naver.data.NaverMapResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface NaverMapService {

//    # Naver cloud platform (Map )
//    Client ID (X-NCP-APIGW-API-KEY-ID) : z4snw8l5kw
//    Client Secret(X-NCP-APIGW-API-KEY) : S8F7MAibXEtPzvpI43RRBPambZ2BJcEKpLybYhPm
// https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode

    @GET("/map-geocode/v2/geocode")
    suspend fun searchAddress(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("query") query: String
    ): Response<NaverMapResult>
}