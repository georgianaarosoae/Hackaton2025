package com.example.hackaton2025.data.api

import com.example.hackaton2025.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getPostsForUser(): Response<List<UserDto>>
}