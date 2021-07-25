package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transtour.chofer.model.User
import com.transtour.chofer.repository.network.user.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor (private val api: ApiClient) :ViewModel()  {

    val resultado = MutableLiveData<Boolean>()

    suspend  fun authenticate(user: User,context:Context) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.login(user)

                //val response = UserNetworkAdapter.generateService(context).login(user)
                if (response.isSuccessful) {
                    resultado.postValue(true)
                    //TODO save in
                    val sharedPref = context?.getSharedPreferences(
                        "transtour.mobile", Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString("token", response.body())
                        apply()
                    }

                } else {
                    Log.d("response", response.errorBody().toString());
                    resultado.postValue(false)
                }

            } catch (e: Exception) {
                //resultado.postValue(false)
                Log.d("Exception login", e.localizedMessage)
            }
        }

    }


}