package com.example.test_platform.presentation.screens.quiz.create

import androidx.compose.runtime.Immutable

class QuizCreateScreen {
    @Immutable
    data class State(
        val title: String = "",
        val theme: String = "",
        val questionsCount: Int = 10,
        val duration: Int = 5,

    )
}