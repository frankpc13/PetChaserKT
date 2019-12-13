package com.shibuyaxpress.petchaserkt.network

import com.google.gson.JsonObject
import com.shibuyaxpress.petchaserkt.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PetChaserAPI {

    companion object {
        const val API_BASE_URL = "http://10.0.2.2:3000/api/v1/"
        //const val API_BASE_URL = "http://3.132.117.167:3000/api/v1/"
    }


    @GET("report")
    fun getReportsAsync() : Deferred<Response<DataResponseReport>>

    @GET("reward")
    fun getRewardsAsync() : Deferred<Response<DataResponseReward>>

    @POST("login")
    fun LoginUserAsync() : Deferred<Response<DataResponseLogin>>

    @POST("checkEmail")
    fun checkUserEmailAsync(@Body email:User) : Deferred<Response<DataResponseEmailChecker>>
}