package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class Passanger{
     @SerializedName("nombre")
     var fullName:String? = null

    @SerializedName("documento")
    var document:String? = null

    override fun toString(): String {
        return "name:${fullName} pass:${document}"
    }
 }
