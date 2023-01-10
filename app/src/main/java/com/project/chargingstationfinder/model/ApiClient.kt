package com.project.chargingstationfinder.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    /*private var retrofit: ApiService? = null

    fun getClient(): ApiService? {
        if (retrofit == null)
            retrofit = Retrofit.Builder().baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)

        return retrofit
    }*/
    private lateinit var retrofit: ApiService

    fun getClient(): ApiService {


        return Retrofit.Builder().baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }
}

interface ApiService {

    @GET("poi")
    fun getPois(
        @Query("countrycode") countryCode: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Int,
        @Query("distanceunit") distanceUnit: Int,
        @Query("key") apiKey: String
    ): Call<List<ChargingStation>>
}