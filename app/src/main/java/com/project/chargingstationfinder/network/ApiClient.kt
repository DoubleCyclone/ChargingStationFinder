package com.project.chargingstationfinder.network

import com.project.chargingstationfinder.util.Constant
import com.project.chargingstationfinder.database.entities.ChargingStation
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("poi")
    suspend fun getPois(
        @Query("countrycode") countryCode: String,
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("distance") distance: Int,
        @Query("distanceunit") distanceUnit: Int,
        @Query("key") apiKey: String
    ): Response<List<ChargingStation>>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiClient {

            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder().client(okHttpClient).baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiClient::class.java)
        }
    }
}