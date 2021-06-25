package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("userName")
    var userName:String? = null
    @SerializedName("password")
    var password:String? = null

    override fun toString(): String {
        return "userName:${userName} password:${password}"
    }
}