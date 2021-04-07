package com.transtour.chofer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.Travel
import com.transtour.chofer.repository.network.travel.TravelNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class TravelViewModel: ViewModel()  {
    val resultado = MutableLiveData<Travel>()
    val TAG:String = "TravelViewModel"


    suspend fun  getTravel(){
        withContext(Dispatchers.IO) {
            try {

                val response = TravelNetworkAdapter.apiClient.lastTravel(true)
                if (response.isSuccessful){
                    resultado.postValue(response.body())
                    Log.i(TAG,"last travel ${response.body().toString()} ")
                }else{
                    Log.d(TAG,"no se pudo recuperar el viaje  ${response.errorBody().toString()} ")
                }
                Log.i(TAG, "Job finished");

            }catch (e: Exception){
                e.stackTrace
                Log.i(TAG,"error  ${e.localizedMessage} ")
            }
        }

    }

}