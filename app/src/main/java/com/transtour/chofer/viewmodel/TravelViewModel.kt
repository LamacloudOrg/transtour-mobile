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


        Log.d("TravelViewModel","obteniendo travel")

         val sharedPref = context?.getSharedPreferences(
             "transtour.mobile", Context.MODE_PRIVATE)

         var travelDefault= Travel()
         travelDefault.date=""
         travelDefault.destiny=""
         travelDefault.hour=""
         travelDefault.origin=""
         travelDefault.id=""
         travelDefault.taxiDriver=""
         travelDefault.passenger=""

         val gson = com.google.gson.Gson()
         val json = gson.toJson(travelDefault)


         val travelString = sharedPref?.getString("travel-new",json.toString())
         val travel = gson.fromJson(travelString, Travel::class.java)
         Log.d("travel-desrializado",travel.toString())

         resultado.postValue(travel)



     }


    fun  removeTravel(context: Context){
        val sharedPref = context?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)


        with (sharedPref?.edit()){
            val gson = com.google.gson.Gson()
            var travelDefault= Travel()
            travelDefault.date=""
            travelDefault.destiny=""
            travelDefault.hour=""
            travelDefault.origin=""
            travelDefault.id=""
            travelDefault.taxiDriver=""
            travelDefault.passenger=""
            val json = gson.toJson(travelDefault)
            //travelSet.add(json)
            this?.putString("travel-new", json.toString())
            this?.apply()
        }
    }
}