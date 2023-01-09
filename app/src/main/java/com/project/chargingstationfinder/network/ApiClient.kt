package com.project.chargingstationfinder.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: ApiService? = null

    fun getClient(): ApiService? {
        if (retrofit == null)
            retrofit = Retrofit.Builder().baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)

        return retrofit
    }
}
