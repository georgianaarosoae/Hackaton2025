package com.example.hackaton2025.data.api

import com.example.hackaton2025.data.model.TaskDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HackathonApi {
    @GET("api/tasks/child/{childId}")
    suspend fun getPostsForUser(@Path("childId") childId: Int): Response<List<TaskDto>>
}