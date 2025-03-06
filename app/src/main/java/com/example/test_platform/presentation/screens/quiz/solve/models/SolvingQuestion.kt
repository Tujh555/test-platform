package com.example.test_platform.presentation.screens.quiz.solve.models

import androidx.compose.runtime.Immutable
import com.example.test_platform.domain.test.Question

@Immutable
data class SolvingQuestion(
    val underlying: Question,
    val markedAnswers: Set<String> = emptySet()
)