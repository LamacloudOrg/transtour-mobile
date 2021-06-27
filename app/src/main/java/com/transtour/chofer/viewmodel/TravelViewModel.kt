package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.transtour.chofer.model.Travel


class TravelViewModel: ViewModel()  {
    val resultado = MutableLiveData<Travel>()
    val TAG:String = "TravelViewModel"


    suspend fun  getTravel(context: Context){

        val sharedPref = context?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)
         val travel = sharedPref?.getString("travel-new",null)

        //TODO ver SQLLite Local

        Log.d("TravelViewModel","obteniendo travel")

         travel.let {
             it?.let { it1 -> Log.d("TravelViewModel", it1)
                // val jsonObject: JsonObject = JsonParser().parse(it1).getAsJsonObject()
                 val  gson = Gson()
                 val travel = gson.fromJson(it1, Travel::class.java)
                 Log.d("travel-desrializado",travel.toString())
                 resultado.postValue(travel)
             }

         }

     }


}