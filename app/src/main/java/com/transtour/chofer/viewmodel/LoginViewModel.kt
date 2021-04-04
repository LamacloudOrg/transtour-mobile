package com.transtour.chofer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.User
import com.transtour.chofer.repository.network.ApiClientAdapter
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewModel :ViewModel()  {

    val resultado = MutableLiveData<Boolean>()

    suspend  fun authenticate(user: User) {
        withContext(Dispatchers.IO) {
            try {

                val response = ApiClientAdapter.apiClient.login(user)
                if (response.isSuccessful) {
                    resultado.postValue(true)
                } else {
                    resultado.postValue(false)
                }
            } catch (e: Exception) {
                resultado.postValue(false)
                Log.i("Exception login", e.localizedMessage)
            }
        }

    }


}