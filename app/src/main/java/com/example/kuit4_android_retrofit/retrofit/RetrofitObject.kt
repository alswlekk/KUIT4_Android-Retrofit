package com.example.kuit4_android_retrofit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val BASE_URL = "https://673d9ac20118dbfe8607ee6a.mockapi.io/kuit/"

    // 마지막 슬래쉬까지 꼭 붙여야함
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}