package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class UserNotification constructor(id: Long, fcmToken: String){

    @SerializedName("id")
    var id:Long? = null
    @SerializedName("fcmToken")
    var fcmToken:String? = null

    init {
        this.id = id
        this.fcmToken = fcmToken
    }

    override fun toString(): String {
        return "id:${id} fcmToken:${fcmToken}"
    }
}