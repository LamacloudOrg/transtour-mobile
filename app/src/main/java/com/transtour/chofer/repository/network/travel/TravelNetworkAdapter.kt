package com.transtour.chofer.repository.network.travel

import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TravelNetworkAdapter {



        //val endPoint:String =  Resources.getSystem().getString(R.string.end_point)

        val endPoint:String ="http://172.26.0.1:8088/"


        val httpClient = OkHttpClient.Builder()
                .apply {
                    addNetworkInterceptor(CustomInterceptor())
                }.build()

        var gson = GsonBuilder()
                .setLenient()
                .create()

        val apiClient: ApiClient = Retrofit.Builder()
                .baseUrl(endPoint)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiClient::class.java)

}