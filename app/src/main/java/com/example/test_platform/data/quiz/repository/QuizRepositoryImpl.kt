package com.example.test_platform.data.quiz.repository

import android.util.Log
import com.example.test_platform.data.dto.toDto
import com.example.test_platform.data.quiz.rest.QuizApi
import com.example.test_platform.data.quiz.rest.RegisterQuizRequest
import com.example.test_platform.data.quiz.rest.SolveQuizRequest
import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl(private val quiz: Quiz, private val api: QuizApi) : QuizRepository {
    class Factory @Inject constructor(private val api: QuizApi) : QuizRepository.Factory {
        override fun invoke(p1: Quiz) = QuizRepositoryImpl(p1, api)
    }

    override suspend fun register(rightAnswers: Map<Int, List<Int>>): Result<Unit> {
        val body = RegisterQuizRequest(quiz.toDto(), rightAnswers)
        return api.register(body).onFailure { Log.e("--tag", it.message, it) }
    }

    override suspend fun solve(answers: Map<String, List<String>>): Result<List<Pair<Question, Boolean>>> {
        val body = SolveQuizRequest(quiz.id, answers)
        return api.solve(body).map { right ->
            quiz.questions.mapNotNull { question ->
                right[question.id]?.let { question to it }
            }
        }
    }
}