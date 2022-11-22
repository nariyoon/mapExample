package com.nryoon.mapexample.kakao.network

import com.nryoon.mapexample.kakao.data.KakaoAddrDocument
import com.nryoon.mapexample.kakao.data.KakaoKeyword
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoMapApi {
    companion object {
        private const val KAKAO_API_KEY = "KakaoAK a78fa5eb1b682464b6942280eecd5c07"
        private const val BASE_URL = "https://dapi.kakao.com"
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val kakaoMapApi = retrofit.create(KakaoMapService::class.java)

        suspend fun getKeywordSearchData(query: String): List<KakaoKeyword>? {
            val result = kakaoMapApi.searchKeyword(KAKAO_API_KEY, query)
            return result.body()?.documents
        }

        suspend fun getAddressSearchData(query: String): List<KakaoAddrDocument>? {
            val result = kakaoMapApi.searchAddress(KAKAO_API_KEY, query)
            return result.body()?.documents
        }
    }
}