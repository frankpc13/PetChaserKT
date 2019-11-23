package com.shibuyaxpress.petchaserkt.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIServiceGenerator {

    val petchaserClient by lazy {
        Log.d("WebAccess","Creating Retrofit Client")
        val retrofit = Retrofit.Builder()
            .baseUrl(PetChaserAPI.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        //Create retrofit client and return
        return@lazy retrofit.create(PetChaserAPI::class.java)

    }
}