package com.example.hackaton2025.di

import com.example.hackaton2025.data.api.UserApi
import com.example.hackaton2025.data.repository.EducationRepository
import com.example.hackaton2025.data.repository.EducationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepsitoryModule {
    @Provides
    fun providesBooksRepositoryImpl(impl: EducationRepositoryImpl): EducationRepository = impl
}