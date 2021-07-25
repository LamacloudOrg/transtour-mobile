package com.transtour.chofer.repository.network.voucher

import com.transtour.chofer.model.Signature
import com.transtour.chofer.model.UserRegisterNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiClient {
    @POST("api/service-voucher/v1/voucher/signature")
    suspend fun registerToken(@Body singture:Signature): Response<String>
}