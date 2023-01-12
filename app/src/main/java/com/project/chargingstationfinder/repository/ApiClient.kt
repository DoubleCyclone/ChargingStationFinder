package com.project.chargingstationfinder.repository

import com.project.chargingstationfinder.util.Constant
import com.project.chargingstationfinder.json.ChargingStation
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("poi")
    fun getPois(
        @Query("countrycode") countryCode: String,
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("distance") distance: Int,
        @Query("distanceunit") distanceUnit: Int,
        @Query("key") apiKey: String
    ): Call<List<ChargingStation>>

    companion object {
        operator fun invoke(): ApiClient {

            return Retrofit.Builder().baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiClient::class.java)
        }
    }
}