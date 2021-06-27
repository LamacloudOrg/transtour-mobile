package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName
import java.util.*

 class Travel{

     //@SerializedName("travel_id")
     var id:String? = null
     //@SerializedName("fecha")
     var date:String? = null
     //@SerializedName("hora")
     var hour:String? = null
     //@SerializedName("pasajero")
     var passenger:String? = null
     //@SerializedName("chofer")
     var taxiDriver:String? = null
     //@SerializedName("origin")
     var origin:String? = null
     //@SerializedName("destiny")
     var destiny:String? = null


     override fun toString(): String {
         return "fecha:${date} hora:${hour}  pasajero :${passenger}  chofer:${taxiDriver}"
     }
 }
