package com.transtour.chofer.repository.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientAdapter{
    val apiClient: ApiClient = Retrofit.Builder()
        .baseUrl("http://localhost:8080")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
}