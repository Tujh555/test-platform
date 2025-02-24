package com.example.test_platform.domain.test

import com.example.test_platform.domain.user.User

data class Quiz(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val questions: List<Question>,
    val author: User
)