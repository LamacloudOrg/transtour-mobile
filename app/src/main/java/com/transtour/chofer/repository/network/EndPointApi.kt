package com.transtour.chofer.repository.network

object EndPointApi {

    fun getEndPoint(s:String):String{
        when(s){
            "prod"-> return "https://209.126.85.7:8080/"
            else -> return "https://locahost:8080/"
        }
    }

}