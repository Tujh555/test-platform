package com.example.test_platform.presentation.screens.quiz.solve

import androidx.compose.runtime.Immutable
import com.example.test_platform.domain.test.Quiz
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class QuizSolveScreen {
    @Immutable
    data class State(
        val quiz: Quiz,
        val remainingTime: Duration = quiz.durationMinutes.minutes
    )
}