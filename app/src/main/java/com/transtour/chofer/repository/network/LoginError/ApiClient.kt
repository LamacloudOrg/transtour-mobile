package com.transtour.chofer.repository.network.LoginError

import com.transtour.chofer.model.LoginError
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {
    @Headers("Accept: application/json")
    @POST("api/service-travel/v1/LoginError/insertError")
    suspend fun insert(@Body loginError: LoginError): Response<ResponseBody>
}