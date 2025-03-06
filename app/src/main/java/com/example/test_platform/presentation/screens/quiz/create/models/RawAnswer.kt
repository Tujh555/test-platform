package com.example.test_platform.presentation.screens.quiz.create.models

import androidx.compose.runtime.Immutable
import com.example.test_platform.domain.test.Answer
import java.util.UUID

@Immutable
data class RawAnswer(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val marked: Boolean = false
)

fun RawAnswer.toDomain() = Answer(id, text)