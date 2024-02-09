//AppModule.kt

package com.hazrat.prayertimes.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hazrat.prayertimes.data.location.LocationDao
import com.hazrat.prayertimes.data.location.LocationDatabase
import com.hazrat.prayertimes.data.method.MethodDao
import com.hazrat.prayertimes.data.method.MethodDatabase
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.repository.location.LocationRepository
import com.hazrat.prayertimes.repository.MethodRepository
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
    fun providePrayerTimeRepository(api: PrayerTimeApi, locationRepository: LocationRepository, methodRepository: MethodRepository) =
        PrayerTimeRepository(api, locationRepository, methodRepository)

    @Singleton
    @Provides
    fun providePrayerApi(): PrayerTimeApi {
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
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMethodDatabase(@ApplicationContext context: Context): MethodDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MethodDatabase::class.java,
            "method_database"
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
        return LocationRepository(context, fusedLocationProviderClient, locationDao)
    }


    @Singleton
    @Provides
    fun provideMethodDao(methodDatabase: MethodDatabase): MethodDao {
        return methodDatabase.methodDao()
    }

    @Singleton
    @Provides
    fun provideMethodRepository(
        methodDao: MethodDao
    ): MethodRepository{
        return MethodRepository(methodDao)
    }

}