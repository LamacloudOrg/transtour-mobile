package com.transtour.chofer.repository.network.userNotification


import android.content.Context
import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import com.transtour.chofer.repository.network.Ssl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

object ApiClientAdapter{


    fun generateService(context: Context): ApiClient {
      //  val endPoint:String ="https://209.126.85.7:8080/"
        val endPoint:String ="https://localhost:8080/"

        val allHostsValid = HostnameVerifier { _, _ -> true }

        val httpClient = OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(CustomInterceptor())
                sslSocketFactory(Ssl.generateCetificate(context)?.socketFactory)
                hostnameVerifier(allHostsValid)

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



