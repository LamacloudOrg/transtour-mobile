package com.transtour.chofer.repository.network.user

import com.google.gson.JsonObject
import com.transtour.chofer.model.User
import com.transtour.chofer.model.UserRegisterNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiClient {
    @Headers("Accept: application/json")
    @POST("api/v1/user/oauth/token")
    suspend fun login(@Body user: User ): Response<String>

    @Headers("Accept: application/json")
    @POST("api/v1/user/update/password")
    suspend fun updateUserPassWord(@Body user: UserRegisterNotification ): Response<String>

}