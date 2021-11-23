package com.transtour.chofer.repository.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor(val tokenAuthorization:String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val request: Request = chain.request()
        Log.i("Request", request.toString())

        Log.i("Body", request.body().toString())
        Log.i("Body", request.body()?.contentType().toString())

        if (!request.url().equals("https://209.126.85.7:8080/api/service-user/v1/user/oauth/token")){
            return chain.proceed(chain.request().newBuilder().addHeader("Authorization","Bearer "+tokenAuthorization).build())
        }else return chain.proceed(chain.request())
    }
}


