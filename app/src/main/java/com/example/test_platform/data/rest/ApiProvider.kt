package com.example.test_platform.data.rest

import com.example.test_platform.data.auth.rest.AuthApi
import com.example.test_platform.data.quiz.rest.QuizApi
import com.example.test_platform.data.retrofit.ResultAdapterFactory
import com.example.test_platform.data.user.rest.ProfileApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiProvider {
    @Provides
    @Singleton
    fun retrofit(tokenInterceptor: AuthTokenInterceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultAdapterFactory())
            .client(client)
            .build()
    }

    @Provides
    fun authApi(retrofit: Retrofit): AuthApi = retrofit.create()

    @Provides
    fun profileApi(retrofit: Retrofit): ProfileApi = retrofit.create()

    @Provides
    fun quizApi(retrofit: Retrofit): QuizApi = retrofit.create()
}