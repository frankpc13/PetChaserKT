package com.shibuyaxpress.petchaserkt.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface PetChaserAPI {

    companion object {
        const val API_BASE_URL = "http://3.132.117.167:3000/api/v1/"
    }


    @GET("report")
    fun getReportsAsync() : Deferred<Response<DataResponseReport>>

    @GET("reward")
    fun getRewardsAsync() : Deferred<Response<DataResponseReward>>

    @POST("login")
    fun LoginUserAsync() : Deferred<Response<DataResponseLogin>>

    @POST("check/user")
    fun checkUserEmailAsync(email:String) : Deferred<Response<DataResponseEmailChecker>>
}