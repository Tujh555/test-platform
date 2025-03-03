package com.example.test_platform.domain.test

import com.example.test_platform.domain.user.User
import java.time.Instant

data class Quiz(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val questions: List<Question>,
    val author: User,
    val solvedCount: Int,
    val lastSolvers: List<User>,
    val createdAt: Instant,
    val theme: String
)