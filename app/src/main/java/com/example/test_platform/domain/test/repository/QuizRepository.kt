package com.example.test_platform.domain.test.repository

import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz

interface QuizRepository {
    interface Factory : (Quiz) -> QuizRepository

    suspend fun register(rightAnswers: Map<Int, List<Int>>): Result<Unit>

    suspend fun solve(answers: Map<String, List<String>>): Result<List<Pair<Question, Boolean>>>
}