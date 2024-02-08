//AppModule.kt

package com.hazrat.prayertimes.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hazrat.prayertimes.data.LocationDao
import com.hazrat.prayertimes.data.LocationDatabase
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.repository.LocationRepository
import com.hazrat.prayertimes.repository.PrayerTimeRepository
import com.hazrat.prayertimes.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppMModule {

    @Singleton
    @Provides
    fun providePrayerTimeRepository(api: PrayerTimeApi,locationRepository: LocationRepository)
    = PrayerTimeRepository(api,locationRepository)

    @Singleton
    @Provides
    fun providePrayerApi(): PrayerTimeApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrayerTimeApi::class.java)
    }


    @Singleton
    @Provides
    fun provideLocationDatabase(@ApplicationContext context: Context): LocationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocationDatabase::class.java,
            "location_database"
        ).build()
    }


    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationDao(locationDatabase: LocationDatabase): LocationDao {
        return locationDatabase.locationDao()
    }

    @Provides
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient,
        locationDao: LocationDao
    ): LocationRepository {
        return LocationRepository(context, fusedLocationProviderClient,locationDao)
    }


}