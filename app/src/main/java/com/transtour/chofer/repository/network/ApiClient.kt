package com.transtour.chofer.repository.network

import com.transtour.chofer.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("/users/login")
    suspend fun login(@Body user: User ): Response<String>
}