package com.example.test_platform.presentation.screens.quiz.create

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class RawAnswer(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val marked: Boolean = false
)