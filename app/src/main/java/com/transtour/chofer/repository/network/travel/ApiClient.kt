package com.transtour.chofer.repository.network.travel

import com.transtour.chofer.model.Travel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiClient {
    @Headers("Accept: application/json")
    @GET("api/service-travel/v1/travel")
    suspend fun lastTravel(@Query("last") last:Boolean): Response<Travel>
}