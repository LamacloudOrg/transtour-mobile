package com.transtour.chofer.repository.network.userNotification

import com.transtour.chofer.model.UserRegisterNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiUserNotification {
    @Headers("Accept: application/json")
    @POST("api/service-notification/v1/notification/registerToken")
    suspend fun registerToken(@Body userNotification: UserRegisterNotification ): Response<String>
}