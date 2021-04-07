package com.transtour.chofer.repository.network.travel

import com.transtour.chofer.model.Travel
import com.transtour.chofer.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {
    @Headers("Accept: application/json")
    @GET("v1/travel")
    suspend fun lastTravel(@Query("last") last:Boolean): Response<Travel>
}