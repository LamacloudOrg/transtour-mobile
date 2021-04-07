package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName
import java.util.*

 class Travel{

     @SerializedName("travel_id")
     var id:String? = null
     @SerializedName("fecha")
     var date:String? = null
     @SerializedName("hora")
     var hour:String? = null
     @SerializedName("pasajero")
     var passanger:Passanger? = null
     @SerializedName("chofer")
     var chofer:Chofer? = null


     override fun toString(): String {
         return "fecha:${date} hour:${hour}  passanger :${passanger}  chofer:${chofer}"
     }
 }
