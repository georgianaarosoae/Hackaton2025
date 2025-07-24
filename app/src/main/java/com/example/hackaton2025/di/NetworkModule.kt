package com.example.hackaton2025.di

import com.example.hackaton2025.data.api.UserApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_TYPICODE = "https://jsonplaceholder.typicode.com"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesTypicodeRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_TYPICODE)
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(FlowCallAdapterFactory())
//                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpClient())
            .build()
    }

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

}