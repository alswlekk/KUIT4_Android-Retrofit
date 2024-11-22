package com.example.kuit4_android_retrofit.retrofit.service

import com.example.kuit4_android_retrofit.data.PopularData
import retrofit2.Call
import retrofit2.http.GET

interface HomePopularService {
    @GET("popular")
    fun getPopular(): Call<List<PopularData>>
}