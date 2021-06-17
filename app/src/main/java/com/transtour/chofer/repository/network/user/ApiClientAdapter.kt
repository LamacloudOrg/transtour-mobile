package com.transtour.chofer.repository.network.user


import android.content.Context
import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import com.transtour.chofer.repository.network.Ssl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientAdapter{

    //val endPoint:String =  Resources.getSystem().getString(R.string.end_point)




    fun generateService(context: Context): ApiClient {
        val endPoint:String ="https://209.126.85.7:8080/"


        val httpClient = OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(CustomInterceptor())
                sslSocketFactory(Ssl.generateCetificate(context))
            }.build()

        var gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(endPoint)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiClient::class.java)

    }

}



