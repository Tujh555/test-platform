package com.example.test_platform.presentation.screens.quiz.create

import androidx.compose.runtime.Immutable
import androidx.compose.ui.util.fastAll

@Immutable
data class RawQuestion(
    val text: String = "",
    val answers: List<RawAnswer> = emptyList(),
) {
    val fulled = text.isNotBlank() && answers.fastAll { it.text.isNotBlank() }
}