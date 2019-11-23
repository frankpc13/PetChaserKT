package com.shibuyaxpress.petchaserkt.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PetChaserAPI {

    companion object {
        val API_BASE_URL = "http://10.0.2.2:3000/api/v1/"
    }

    @GET("report")
    fun getReportsAsync() : Deferred<Response<DataResponseReport>>

    @GET("reward")
    fun getRewardsAsync() : Deferred<Response<DataResponseReward>>

}