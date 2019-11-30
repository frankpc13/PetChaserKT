package com.shibuyaxpress.petchaserkt.network

import com.shibuyaxpress.petchaserkt.models.Report
import com.shibuyaxpress.petchaserkt.models.Reward
import com.shibuyaxpress.petchaserkt.models.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponseReport(

    @field:Json(name = "message")
    var message: String,

    @field:Json(name = "data")
    var data: List<Report>?,

    @field:Json(name = "statusCode")
    var statusCode: Int
)

@JsonClass(generateAdapter = true)
data class DataResponseReward(
    @field:Json(name = "message")
    var message: String,
    @field:Json(name = "data")
    var data: List<Reward>?,
    @field:Json(name = "statusCode")
    var statusCode: Int
)

@JsonClass(generateAdapter = true)
data class DataResponseLogin(
    @field:Json(name = "message")
    var message: String,
    @field:Json(name = "data")
    var data: User?,
    @field:Json(name = "statusCode")
    var statusCode: Int
)