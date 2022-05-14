package com.davidbronn.movietmdb.data.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status_code")
    var statusCode: Int = 0,
    @SerializedName("status_message")
    var statusMessage: String = "",
    @SerializedName("success")
    var success: Boolean = false
)