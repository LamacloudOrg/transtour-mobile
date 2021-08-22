package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.Signature
import com.transtour.chofer.model.TravelTaxes
import com.transtour.chofer.repository.network.travel.TravelNetworkAdapter
import com.transtour.chofer.repository.network.voucher.VoucherNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoucherViewModel():ViewModel() {
    val signerResult = MutableLiveData<Boolean>()
    val travelResult = MutableLiveData<Boolean>()
    val TAG:String = "VoucherViewModel"

    suspend fun sign(signature: Signature,context:Context){
        withContext(Dispatchers.IO) {
            try {
                val response = VoucherNetworkAdapter.generateService(context).signVoucher(signature)
                Log.d(TAG, response.code().toString())

                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                    signerResult.postValue(true)

                } else{
                    Log.d(TAG, response.errorBody().toString())
                    signerResult.postValue(false)
                }

            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
                signerResult.postValue(false)
            }

        }
    }

    suspend fun updateTaxes(travel: TravelTaxes,context:Context){
        withContext(Dispatchers.IO) {
            try {
                val response = TravelNetworkAdapter.generateService(context).update(travel)
                Log.d(TAG, response.code().toString())

                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                    travelResult.postValue(true)

                } else{
                    Log.d(TAG, response.errorBody().toString())
                    travelResult.postValue(false)
                }

            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
                travelResult.postValue(false)
            }

        }
    }

}