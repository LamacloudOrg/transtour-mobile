package com.transtour.chofer.repository.network.travel

import com.transtour.chofer.model.Travel
import com.transtour.chofer.model.TravelTaxes
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {
    @Headers("Accept: application/json")
    @POST("api/service-travel/v1/travel/saveTaxes")
    suspend fun update(@Body travel: TravelTaxes): Response<ResponseBody>

    @Headers("Accept: application/json")
    @GET("api/service-travel/v1/travel")
    suspend fun update(@QueryName last:Boolean): Response<Travel>
}