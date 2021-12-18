package com.transtour.chofer.model

import com.google.gson.annotations.SerializedName

class TravelTaxes constructor(id:Long?,waitingTime:String?,parkingAmount:String?,tolAmount:String?,taxForReturn:String?){

    @SerializedName("orderNumber")
    var id:Long? = null
    @SerializedName("whitingTime")
    var waitingTime:String? = null
    @SerializedName("parkingAmount")
    var parkingAmount:String? = null
    @SerializedName("toll")
    var tolAmount:String? = null
    @SerializedName("taxForReturn")
    var taxForReturn:String? = null



    override fun toString(): String {
        return "id:${id} waitingTime:${waitingTime}  parkingAmount :${parkingAmount}  tolAmount:${tolAmount} taxForReturn ${taxForReturn}"
    }
}
