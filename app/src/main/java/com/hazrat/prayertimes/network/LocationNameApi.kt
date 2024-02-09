package com.hazrat.prayertimes.network

import com.hazrat.prayertimes.model.locationmodel.LocationNameFinder
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface LocationNameApi {

    @GET("reverse")
    suspend fun getLocationName(
        @Query("format") format: String = "json",
        @Query("lat") lat: Double,
        @Query("lon") lon:Double
    ): LocationNameFinder
}