package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("userName")
    var name:String? = null
    @SerializedName("password")
    var password:String? = null

    override fun toString(): String {
        return "name:${name} pass:${password}"
    }
}