package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class Chofer {
    @SerializedName("nombre")
    var fullName:String? = null

    @SerializedName("telefono")
    var telefono:String? = null

    override fun toString(): String {
        return "name:${fullName} phone:${telefono}"
    }

}