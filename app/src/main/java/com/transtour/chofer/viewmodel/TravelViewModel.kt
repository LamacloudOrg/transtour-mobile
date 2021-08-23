package com.transtour.chofer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.transtour.chofer.model.Travel


class TravelViewModel(): ViewModel()  {
    val resultado = MutableLiveData<Travel>()
    val TAG:String = "TravelViewModel"


     fun  getTravel(context: Context){

        val sharedPref = context?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)
         val travels = sharedPref?.getStringSet("travelList",null)

        //TODO ver SQLLite Local

        Log.d("TravelViewModel","obteniendo travel")

         travels.let {
             it?.let { it1 ->
                 if (!it1.isEmpty()){
                     Log.d("TravelViewModel", it1.first().toString())
                     // val jsonObject: JsonObject = JsonParser().parse(it1).getAsJsonObject()
                     val sec = it1 as MutableSet
                     val  gson = Gson()
                     val travel = gson.fromJson(sec.first(), Travel::class.java)
                     Log.d("travel-desrializado",travel.toString())
                     resultado.postValue(travel)
                 }

             }

         }

     }


    fun  removeTravel(context: Context){

        val sharedPref = context?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)
        val travels = sharedPref?.getStringSet("travelList",null)

        //TODO ver SQLLite Local

        Log.d("TravelViewModel","eliminando travel")

        travels.let {
            it?.let { it1 ->
                if (!it1.isEmpty()){
                    Log.d("TravelViewModel", it1.first())
                    val sec = it1 as MutableSet
                    sec.remove(it1.first())
                    with (sharedPref?.edit()){
                        this?.putStringSet("travelList", sec)
                        this?.apply()
                    }
                }
            }
        }
    }
}