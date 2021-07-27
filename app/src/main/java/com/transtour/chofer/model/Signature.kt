package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class Signature( @SerializedName("travelId")
                 var fileName:String ="",
                 @SerializedName("base64") var file:String ="",
                 @SerializedName("contentType")
                 var type:String=""){

    override fun toString(): String {
        return "fileName:${fileName} file:${file} type${type}"
    }
}
