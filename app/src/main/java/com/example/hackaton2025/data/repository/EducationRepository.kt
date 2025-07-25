package com.example.hackaton2025.data.repository

import android.util.Log
import com.example.hackaton2025.data.api.HackathonApi
import com.example.hackaton2025.data.api.UserApi
import com.example.hackaton2025.data.model.TaskDto
import com.example.hackaton2025.data.model.UserDto
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

interface EducationRepository {
    fun getUsers(): Flow<List<UserDto>>

    fun getChildTasks(childId: Int): Flow<List<TaskDto>>
}

class EducationRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val hackathonApi: HackathonApi
) : EducationRepository {
    override fun getUsers(): Flow<List<UserDto>> {
        return flow {
            val response = api.getPostsForUser()

            if (response.isSuccessful)
                response.body()?.let {
                    emit(it)
                }
            else
                Log.e("repo", "fauled to get: ${response.errorBody()?.string()}")
//            coroutineScope {
//                while (isActive) {
//                    val response = api.getPostsForUser()
//
//                    if (response.isSuccessful)
//                        response.body()?.let {
//                            emit(it)
//                        }
//                    else
//                        Log.e("repo", "fauled to get: ${response.errorBody()?.string()}")
//
//                    delay(60_000)
//                }
//            }
        }
    }

    override fun getChildTasks(childId: Int): Flow<List<TaskDto>> {
        return flow {
            val response = hackathonApi.getPostsForUser(childId)

            if (response.isSuccessful)
                response.body()?.let {
                    emit(it)
                }
            else
                Log.e("repo", "fauled to get: ${response.errorBody()?.string()}")
        }
    }
}