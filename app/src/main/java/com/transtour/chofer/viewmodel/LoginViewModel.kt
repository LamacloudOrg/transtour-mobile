package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.User
import com.transtour.chofer.repository.network.user.ApiClientAdapter
import kotlinx.coroutines.*

class LoginViewModel :ViewModel()  {

    val resultado = MutableLiveData<Boolean>()

    suspend  fun authenticate(user: User,context:Context) {
        withContext(Dispatchers.IO) {
            try {

                val response = ApiClientAdapter.generateService(context).login(user)
                if (response.isSuccessful) {
                    resultado.postValue(true)
                } else {
                    resultado.postValue(false)
                }

                Log.i("response", response.message());


            } catch (e: Exception) {
                resultado.postValue(false)
                Log.i("Exception login", e.localizedMessage)
            }
        }

    }


}