package com.example.test_platform.di

import com.example.test_platform.presentation.MainActivity
import com.example.test_platform.presentation.error.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {
    @Binds
    @Singleton
    fun errorHandler(impl: MainActivity.Handler): ErrorHandler
}