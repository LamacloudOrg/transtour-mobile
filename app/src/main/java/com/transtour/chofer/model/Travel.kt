package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName
import java.util.*

 class Travel{
     @SerializedName("fecha")
     var date:String? = null
     @SerializedName("hora")
     var hour:String? = null
     @SerializedName("hora")
     var passanger:Passanger? = null

     override fun toString(): String {
         return "name:${date} pass:${hour}  passanger :${passanger}"
     }
 }
