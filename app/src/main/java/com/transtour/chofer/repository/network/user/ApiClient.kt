package com.transtour.chofer.repository.network.user

import com.google.gson.JsonObject
import com.transtour.chofer.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiClient {
    @Headers("Accept: application/json")
    @POST("v1/user/login")
    suspend fun login(@Body user: User ): Response<String>
}