package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("dni")
    var dni:Long? = null
    @SerializedName("password")
    var password:String? = null

    override fun toString(): String {
        return "user :${dni} password:${password}"
    }
}