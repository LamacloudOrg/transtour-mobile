package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.Signature
import com.transtour.chofer.repository.network.voucher.VoucherNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoucherViewModel():ViewModel() {
    val resultado = MutableLiveData<Boolean>()
    val TAG:String = "VoucherViewModel"

    suspend fun sign(signature: Signature,context:Context){
        withContext(Dispatchers.IO) {
            try {
                val response = VoucherNetworkAdapter.generateService(context).signVoucher(signature)
                Log.d(TAG, response.code().toString())

                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                    resultado.postValue(true)

                } else{
                    Log.d(TAG, response.errorBody().toString())
                    resultado.postValue(false)
                }

            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
                resultado.postValue(false)
            }

        }
    }

}