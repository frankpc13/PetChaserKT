package com.shibuyaxpress.petchaserkt.network

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {
    var token = ""

    fun Token(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            if (!token.isNotEmpty()) {
                val finalToken = "Bearer $token"
                request =  request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}