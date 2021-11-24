package com.transtour.chofer.model

class UserRegisterNotification(val id: Long, val fcmToken: String){
    override fun toString(): String {
        return "id:${id} fcmToken:${fcmToken}"
    }
}