package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class Travel{



     //@SerializedName("travel_id")
     var id:String? = null
     //@SerializedName("fecha")
     var date:String? = null
     @SerializedName("time")
     var hour:String? = null
     @SerializedName("'passenger'")
     var passenger:String? = null
     @SerializedName("carDriver")
     var taxiDriver:String? = null
     //@SerializedName("origin")
     var origin:String? = null
     //@SerializedName("destiny")
     var destiny:String? = null
     var observation: String ? = null
     var company:String? = null
     var netAmount:String? = null
     var waitingTime:String? = null
     var toll:String? = null
     var parkingAmount:String? = null
     var takForReturn:String? = null


     override fun toString(): String {
         return "fecha:${date} hora:${hour}  pasajero :${passenger}  chofer:${taxiDriver}"
     }
 }
