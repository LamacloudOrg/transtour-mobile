package com.transtour.chofer.repository.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val request: Request = chain.request()
        Log.i("Request", request.toString())

        Log.i("Body", request.body().toString())
        Log.i("Body", request.body()?.contentType().toString())

        return chain.proceed(chain.request())
    }
}


