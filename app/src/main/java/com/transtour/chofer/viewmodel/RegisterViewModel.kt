package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.UserRegister
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.repository.network.user.ApiClient
import com.transtour.chofer.repository.network.userNotification.ApiUserNotification
import com.transtour.chofer.repository.network.userNotification.UserNotificationNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel  @Inject constructor(private val apiClient: ApiClient, private val apiUserNotification: ApiUserNotification) : ViewModel() {

    val resultado = MutableLiveData<Boolean>()
   // val token = MutableLiveData<String>()

    suspend fun registUser (user: UserRegisterNotification,password: String)= coroutineScope {
       val res1 =  async {updateUserPassWord(user.dni!!, password)}
        val res2 = async { setFcmToken(user)}

        val result = res1.await() + res2.await()
        if (result == 2){
            resultado.postValue(true)
        }else{
            resultado.postValue(false)

            if (res1.getCompleted() == 0){
                Log.d("register","fallo la actualizacion del password")
            }

            if (res2.getCompleted() == 0){
                Log.d("register","fallo la actualizacion del token")
            }

        }

    }

    @OptIn
    suspend fun updateUserPassWord(userId:Long, password:String) : Int{
        var res : Int = 0;
        withContext(Dispatchers.IO) {
            try {
                val userRegister:UserRegister  = UserRegister(userId,password)
                val response = apiClient.updateUserPassWord(userRegister)
                Log.d("update pass response", response.code().toString())
                if (response.isSuccessful) {
                    res = 1
                    Log.d("update pass body", response.body().toString())
                } else{
                    res = 0
                    Log.d("update pass  error", response.errorBody().toString())

                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

    @OptIn
    suspend fun setFcmToken(user:UserRegisterNotification) : Int{
        var res : Int = 0;
        withContext(Dispatchers.IO) {
            try {
                val response = apiUserNotification.registerToken(user)
                Log.d("setFcmToken  response", response.code().toString())

                if (response.isSuccessful) {
                    Log.d("setFcmToken  response", response.body().toString())

                    res = 1
                } else{
                    res = 0
                    Log.d("fcmToker error", response.errorBody().toString())

                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }


}