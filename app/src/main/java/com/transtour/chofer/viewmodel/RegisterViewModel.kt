package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.UserRegister
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.repository.network.user.UserNetworkAdapter
import com.transtour.chofer.repository.network.userNotification.UserNotificationNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class RegisterViewModel() : ViewModel() {

    val resultado = MutableLiveData<Boolean>()

    suspend fun registToken(user:UserRegisterNotification, context: Context) : Int{
        var res : Int = 0;
        withContext(Dispatchers.IO) {
            try {
                val response = UserNotificationNetworkAdapter.generateService(context).registerToken(user)
                Log.d("setFcmToken  response", response.code().toString())

                if (response.isSuccessful) {
                    Log.d("setFcmToken  response", response.body().toString())

                    resultado.postValue(true)
                } else{
                    resultado.postValue(false)
                    Log.d("fcmToker error", response.errorBody().toString())

                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

    suspend fun tokenRegistration() {
        TODO("Not yet implemented")
    }

}