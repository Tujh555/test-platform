package com.example.test_platform.data.quiz.rest

import com.example.test_platform.data.dto.QuizDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface QuizApi {
    @GET("quizzes/all")
    suspend fun quizzes(
        @Query("limit") limit: Int,
        @Query("cursor") cursor: String
    ): Result<List<QuizDto>>

    @GET("quizzes/own")
    suspend fun ownQuizzes(
        @Query("limit") limit: Int,
        @Query("cursor") cursor: String
    ): Result<List<QuizDto>>

    @GET("quizzes/search")
    suspend fun quizzes(
        @Query("search_by") query: String,
        @Query("limit") limit: Int,
        @Query("cursor") cursor: String
    ): Result<List<QuizDto>>

    @PUT("quizzes/register")
    suspend fun register(@Body body: RegisterQuizRequest): Result<Unit>

    @POST("quizzes/solve")
    suspend fun solve(@Body body: SolveQuizRequest): Result<Map<String, Boolean>>
}