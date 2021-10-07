package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class LoginError(
    repoName:String?,
    stackTrace: String?,
    comment:String?){

    @SerializedName("repoName")
    var repoName:String? = null
    @SerializedName("stackTrace")
    var stackTrace:String? = null
    @SerializedName("comment")
    var comment:String? = null
}