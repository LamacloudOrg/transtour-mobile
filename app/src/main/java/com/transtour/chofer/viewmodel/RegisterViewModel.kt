package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.repository.network.user.UserNetworkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    val resultado = MutableLiveData<Boolean>()

    suspend fun execute (user: User, context: Context){
       val res1 = updateUserPassWord(user, context)
        val res2 = setFcmToken(user, context)
    }

    suspend fun updateUserPassWord(user: User, context: Context) : Boolean{
        var res : Boolean = false;
        withContext(Dispatchers.IO) {
            try {
                val response = UserNetworkAdapter.generateService(context).updateUserPassWord(user)
                if (response.isSuccessful) {
                    res = true
                } else{
                    res = false
                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

    suspend fun setFcmToken(user: User, context: Context) : Boolean{
        var res : Boolean = false;
        withContext(Dispatchers.IO) {
            try {

                val userRegisterNotification = com.transtour.chofer.model.UserNotification(user.userName, token)
                val response = UserNetworkAdapter.generateService(baseContext).registerToken(userNotification)
                if (response.isSuccessful) {
                    res = true
                } else{
                    res = false
                }

            } catch (e: Exception) {
                Log.d("Exception login", e.localizedMessage)
            }
        }
        return res;
    }

}