//PrayerTimeApi.kt

package com.hazrat.prayertimes.network
import com.hazrat.prayertimes.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface PrayerTimeApi {
    ///https://api.aladhan.com/v1/timings/03-02-2024?latitude=24.51575&longitude=88.013360&method=2

    @GET("{date}")
    suspend fun getPrayerTimes(
        @Path("date") date: String,
        @Query("latitude")latitude:String,
        @Query("longitude")longitude:String,
        @Query("method")method:Int
    ): ApiResponse

}