package com.davidbronn.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    @SerialName("status_code")
    var statusCode: Int = 0,
    @SerialName("status_message")
    var statusMessage: String = "",
    @SerialName("success")
    var success: Boolean = false
)