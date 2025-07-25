package com.example.hackaton2025.di

import com.example.hackaton2025.data.api.HackathonApi
import com.example.hackaton2025.data.api.UserApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

private const val BASE_URL_TYPICODE = "https://jsonplaceholder.typicode.com"
private const val NAMED_TYPICODE = "typicode"

private const val BASE_URL_HACKA = "https://db-hack-be-144591507519.europe-west1.run.app/api"
private const val NAMED_HACKA = "hacka"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named(NAMED_TYPICODE)
    fun providesTypicodeRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_TYPICODE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient())
            .build()
    }

    @Provides
    @Named(NAMED_HACKA)
    fun providesHackathonRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_HACKA)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient())
            .build()
    }

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesUserApi(@Named(NAMED_TYPICODE) retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun providesHackathonApi(@Named(NAMED_HACKA) retrofit: Retrofit): HackathonApi = retrofit.create(HackathonApi::class.java)

}