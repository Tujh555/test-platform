package com.example.test_platform.data.quiz.rest

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.test_platform.data.dto.AnswerDto
import com.example.test_platform.data.dto.QuestionDto
import com.example.test_platform.data.dto.QuizDto
import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.domain.test.Question
import kotlinx.coroutines.delay
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("quizzes/register")
    suspend fun register(@Body body: RegisterQuizRequest): Result<Unit>

    @POST("quizzes/solve")
    suspend fun solve(@Body body: SolveQuizRequest): Result<Map<String, Boolean>>

    companion object {
        fun lorem(count: Int) = LoremIpsum(count).values.joinToString(separator = "")
        fun randomId() = UUID.randomUUID().toString()
        
        operator fun invoke(): QuizApi = object : QuizApi {
            private val pagesLimit = 10
            private var allLoaded = 0
            private var ownLoaded = 0
            private var searchLoaded = 0

            private fun list(count: Int) = List(count) {
                QuizDto(
                    id = randomId(),
                    title = "Quiz title #$it",
                    durationMinutes = nextInt(5, 20),
                    questions = listOf(
                        QuestionDto(
                            id = randomId(),
                            text = lorem(20),
                            variants = listOf(
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2))
                            ),
                            type = Question.Type.Multiple.name
                        ),
                        QuestionDto(
                            id = randomId(),
                            text = lorem(2),
                            variants = listOf(
                                AnswerDto(randomId(), lorem(4)),
                                AnswerDto(randomId(), lorem(4)),
                                AnswerDto(randomId(), lorem(4))
                            ),
                            type = Question.Type.Single.name
                        ),
                        QuestionDto(
                            id = randomId(),
                            text = lorem(20),
                            variants = listOf(
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2))
                            ),
                            type = Question.Type.Multiple.name
                        ),
                        QuestionDto(
                            id = randomId(),
                            text = lorem(20),
                            variants = listOf(
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                                AnswerDto(randomId(), lorem(2)),
                            ),
                            type = Question.Type.Multiple.name
                        )
                    ),
                    author = UserDto(
                        id = randomId(),
                        avatar = null,
                        name = "User name #$it"
                    ),
                    solvedCount = 10,
                    lastSolvers = List(nextInt(0, 3)) { i ->
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

            override suspend fun quizzes(limit: Int, cursor: String): Result<List<QuizDto>> {
                delay(1000)
                val count = if (allLoaded == pagesLimit) limit - 1 else limit
                return Result.success(list(count)).also { allLoaded++ }
            }

            override suspend fun quizzes(
                query: String,
                limit: Int,
                cursor: String
            ): Result<List<QuizDto>> {
                delay(1000)
                val count = if (searchLoaded == pagesLimit) limit - 1 else limit
                return Result.success(list(count)).also { searchLoaded++ }
            }

            override suspend fun ownQuizzes(limit: Int, cursor: String): Result<List<QuizDto>> {
                delay(1000)
                val count = if (ownLoaded >= pagesLimit) limit / 2 else limit
                ownLoaded++
                return Result.success(list(count))
            }

            override suspend fun solve(body: SolveQuizRequest): Result<Map<String, Boolean>> {
                delay(2000)
                return Result.success(emptyMap())
            }

            override suspend fun register(body: RegisterQuizRequest): Result<Unit> {
                delay(2000)
                return Result.success(Unit)
            }
        }
    }
}