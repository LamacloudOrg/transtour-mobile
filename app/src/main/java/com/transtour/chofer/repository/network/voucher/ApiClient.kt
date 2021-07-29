package com.transtour.chofer.repository.network.voucher

import com.transtour.chofer.model.Signature
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST("api/service-voucher/v1/voucher/saveSignature")
    suspend fun signVoucher(@Body singture: Signature): Response<String>
}