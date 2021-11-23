package com.transtour.chofer.repository.network.userNotification


import android.content.Context
import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import com.transtour.chofer.repository.network.EndPointApi
import com.transtour.chofer.repository.network.Ssl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier

object UserNotificationNetworkAdapter{

    fun generateService(context: Context): ApiClient {

        val allHostsValid = HostnameVerifier { _, _ -> true }

        val sharedPref = context?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        var tokenAutorization: String? = sharedPref?.getString("token","");

        val httpClient = OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(CustomInterceptor(tokenAutorization))
                sslSocketFactory(Ssl.generateCetificate(context)?.socketFactory)
                hostnameVerifier(allHostsValid)

            }.build()

        var gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(EndPointApi.getEndPoint("prod"))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiClient::class.java)

    }

}



