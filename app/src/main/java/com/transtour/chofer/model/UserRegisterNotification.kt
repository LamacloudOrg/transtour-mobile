package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class UserRegisterNotification(id: Long?, fcmToken: String?){

    @SerializedName("id")
    var dni:Long? = null
    @SerializedName("fcmToken")
    var fcmToken:String? = null

    init {
        this.dni = id
        this.fcmToken = fcmToken
    }

    override fun toString(): String {
        return "id:${dni} fcmToken:${fcmToken}"
    }
}