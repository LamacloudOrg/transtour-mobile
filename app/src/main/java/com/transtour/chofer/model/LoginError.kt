package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class LoginError constructor(dateCreated:String?,time:String?,repoName:String?,stackTrace:String?,comment:String?){

    @SerializedName("dateCreated")
    var dateCreated:String? = null
    @SerializedName("time")
    var time:String? = null
    @SerializedName("repoName")
    var repoName:String? = null
    @SerializedName("stackTrace")
    var stackTrace:String? = null
    @SerializedName("comment")
    var comment:String? = null
}