package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.UserNotification
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.repository.network.user.UserNetworkAdapter
import com.transtour.chofer.repository.network.userNotification.UserNotificationNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    val resultado = MutableLiveData<Boolean>()

    suspend fun execute (user: UserRegisterNotification, token:String, context: Context)= coroutineScope {
       val res1 =  async {updateUserPassWord(user, context)}
        val res2 = async { setFcmToken(token,user,context)}

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
    suspend fun updateUserPassWord(user: UserRegisterNotification, context: Context) : Int{
        var res : Int = 0;
        withContext(Dispatchers.IO) {
            try {
                val response = UserNetworkAdapter.generateService(context).updateUserPassWord(user)
                if (response.isSuccessful) {
                    res = 1
                } else{
                    res = 0
                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

    @OptIn
    suspend fun setFcmToken(token:String, user:UserRegisterNotification, context: Context) : Int{
        var res : Int = 0;
        withContext(Dispatchers.IO) {
            try {
                val userNotification = UserNotification(user.dni!!,token)
                val response = UserNotificationNetworkAdapter.generateService(context).registerToken(userNotification)
                if (response.isSuccessful) {
                    res = 1
                } else{
                    res = 0
                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

}