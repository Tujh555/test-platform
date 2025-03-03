package com.example.test_platform.data.dto

import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.user.User
import com.example.test_platform.toInstantOrNow
import com.google.gson.annotations.SerializedName

class QuizDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("duration")
    val durationMinutes: Int?,
    @SerializedName("questions")
    val questions: List<QuestionDto>?,
    @SerializedName("author")
    val author: UserDto?,
    @SerializedName("solved_count")
    val solvedCount: Int?,
    @SerializedName("last_solvers")
    val lastSolvers: List<UserDto>?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("theme")
    val theme: String?
)

fun QuizDto.toDomain() = Quiz(
    id = id,
    title = title.orEmpty(),
    durationMinutes = durationMinutes ?: 5,
    questions = questions.orEmpty().map(QuestionDto::toDomain),
    author = author?.toDomain() ?: UserDto.unknown.toDomain(),
    solvedCount = solvedCount ?: 0,
    lastSolvers = lastSolvers.orEmpty().map(UserDto::toDomain),
    createdAt = createdAt.toInstantOrNow(),
    theme = theme ?: "unknown"
)

fun Quiz.toDto() = QuizDto(
    id = id,
    title = title,
    durationMinutes = durationMinutes,
    questions = questions.map(Question::toDto),
    author = author.toDto(),
    solvedCount = solvedCount,
    lastSolvers = lastSolvers.map(User::toDto),
    createdAt = createdAt.toString(),
    theme = theme
)

fun List<QuizDto>.toDomain() = map(QuizDto::toDomain)