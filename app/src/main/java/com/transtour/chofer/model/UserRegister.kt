package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class UserRegister constructor(id: Long, password: String?){

    @SerializedName("id")
    var id:Long? = null
    @SerializedName("password")
    var password:String? = null

    init {
        this.id = id
        this.password = password
    }

    override fun toString(): String {
        return "id:${id} password:${password}"
    }
}