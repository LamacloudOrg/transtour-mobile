package com.transtour.chofer.repository.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor(val tokenAuthorization:String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val request: Request = chain.request()
        Log.d("Request", request.toString())

        Log.d("Body", request.body().toString())
        Log.d("Body", request.body()?.contentType().toString())

        if (!tokenAuthorization.equals("")){
            return chain.proceed(request.newBuilder().addHeader("Authorization","Bearer "+tokenAuthorization).build())
        }else return chain.proceed(request)
   }
}


