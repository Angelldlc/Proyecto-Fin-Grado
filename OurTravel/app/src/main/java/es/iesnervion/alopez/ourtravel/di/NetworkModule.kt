package es.iesnervion.alopez.ourtravel.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.iesnervion.alopez.ourtravel.BuildConfig
import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSource
import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSourceImpl
import es.iesnervion.alopez.ourtravel.data.network.ApiService
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    private const val BASE_URL = "https://apicitiesourtravel2.azurewebsites.net/api/"
//
//    @Provides
//    @Singleton
//    fun provideCitiesDataSource(retrofit: Retrofit): CitiesDataSource = CitiesDataSourceImpl(retrofit)
//
//    @Provides
//    @Singleton
//    fun retrofitProvider(): Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create())
//        .build()
//}
