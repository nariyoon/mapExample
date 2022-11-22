package com.nryoon.mapexample.naver.network

import com.nryoon.mapexample.naver.data.NaverMapAddress
import com.nryoon.mapexample.naver.data.NaverSearchResultItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverApi {
    companion object {
        private const val MAP_API_CLIENT_ID = "z4snw8l5kw"
        private const val MAP_API_CLIENT_SECRET = "S8F7MAibXEtPzvpI43RRBPambZ2BJcEKpLybYhPm"
        private const val SEARCH_API_CLIENT_ID = "hQP3XeFXQgSeIXOKxq0s"
        private const val SEARCH_API_CLIENT_SECRET = "KXR9OgZGAk"

        private const val MAP_URL = "https://naveropenapi.apigw.ntruss.com/"
        private const val SEARCH_URL = "https://openapi.naver.com/v1/"

        private val searchRetrofit = Retrofit.Builder()
            .baseUrl(SEARCH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val mapRetrofit = Retrofit.Builder()
            .baseUrl(MAP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val naverSearchApi = searchRetrofit.create(NaverSearchService::class.java)
        private val naverMapApi = mapRetrofit.create(NaverMapService::class.java)

        suspend fun getNaverMapAddresses(query: String): List<NaverMapAddress>? {
            val result = naverMapApi
                .searchAddress(MAP_API_CLIENT_ID, MAP_API_CLIENT_SECRET, query = query)
            return result.body()?.addresses
        }

        suspend fun getNaverSearchResultItems(query: String): List<NaverSearchResultItem>? {
            val result = naverSearchApi
                .getNaverSearchData(clientId = SEARCH_API_CLIENT_ID, clientSecret = SEARCH_API_CLIENT_SECRET, query = query, display = 5)
            return result.body()?.items
        }
    }
}