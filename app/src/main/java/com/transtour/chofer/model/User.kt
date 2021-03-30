package com.transtour.chofer.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Keep
class User {
    @SerializedName("userName")
    @Expose
    var name:String? = null
    @SerializedName("password")
    @Expose
    var password:String? = null
}