package com.example.test_platform.data.quiz.rest

import com.example.test_platform.data.dto.QuizDto
import com.example.test_platform.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.Instant
import java.util.UUID
import kotlin.random.Random.Default.nextInt

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

    companion object Mock : QuizApi {
        override suspend fun quizzes(limit: Int, cursor: String): Result<List<QuizDto>> {
            if (nextInt(0, 10) % 2 == 0) {
                val list = List(limit) {
                    QuizDto(
                        id = UUID.randomUUID().toString(),
                        title = "Quiz title #$it",
                        durationMinutes = nextInt(5, 100),
                        questions = listOf(),
                        author = UserDto(
                            id = UUID.randomUUID().toString(),
                            avatar = null,
                            name = "User name #$it"
                        ),
                        solvedCount = 10,
                        lastSolvers = List(nextInt(0, 5)) { i ->
                            UserDto(
                                id = UUID.randomUUID().toString(),
                                avatar = null,
                                name = "User name #$i"
                            )
                        },
                        createdAt = Instant.now().toString(),
                        theme = "Quiz theme #$it"
                    )
                }

                return Result.success(list)
            }

            return Result.failure(IllegalStateException())
        }

        override suspend fun quizzes(
            query: String,
            limit: Int,
            cursor: String
        ): Result<List<QuizDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun ownQuizzes(limit: Int, cursor: String): Result<List<QuizDto>> {
            if (nextInt(0, 10) % 2 == 0) {
                val list = List(limit) {
                    QuizDto(
                        id = UUID.randomUUID().toString(),
                        title = "Quiz title #$it",
                        durationMinutes = nextInt(5, 100),
                        questions = listOf(),
                        author = UserDto(
                            id = UUID.randomUUID().toString(),
                            avatar = null,
                            name = "User name #$it"
                        ),
                        solvedCount = 10,
                        lastSolvers = List(nextInt(0, 5)) { i ->
                            UserDto(
                                id = UUID.randomUUID().toString(),
                                avatar = null,
                                name = "User name #$i"
                            )
                        },
                        createdAt = Instant.now().toString(),
                        theme = "Quiz theme #$it"
                    )
                }

                return Result.success(list)
            }

            return Result.failure(IllegalStateException())
        }
    }
}