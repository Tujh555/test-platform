package com.example.test_platform.presentation.screens.quiz.create.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.util.fastAll
import com.example.test_platform.domain.test.Question

@Immutable
data class RawQuestion(
    val text: String = "",
    val answers: List<RawAnswer> = emptyList(),
    val type: Question.Type = Question.Type.Multiple
) {
    val fulled = text.isNotBlank() && answers.isNotEmpty() && answers.fastAll { it.text.isNotBlank() }
}

fun RawQuestion.toDomain() = Question(
    id = "",
    text = text,
    variants = answers.map(RawAnswer::toDomain),
    type = type
)