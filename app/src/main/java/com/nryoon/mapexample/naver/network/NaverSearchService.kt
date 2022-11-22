            package com.nryoon.mapexample.naver.network

import com.nryoon.mapexample.naver.data.NaverSearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverSearchService {
    //    # Naver open api
    //    Clinet ID : hQP3XeFXQgSeIXOKxq0s
   //    Clinet Secret : KXR9OgZGAk

    @GET("search/local.json")
    suspend fun getNaverSearchData(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int
    ): Response<NaverSearchResult>
}