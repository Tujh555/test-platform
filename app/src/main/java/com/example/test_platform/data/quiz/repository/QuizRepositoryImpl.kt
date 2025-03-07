package com.example.test_platform.data.quiz.repository

import com.example.test_platform.data.dto.toDto
import com.example.test_platform.data.quiz.rest.QuizApi
import com.example.test_platform.data.quiz.rest.RegisterQuizRequest
import com.example.test_platform.data.quiz.rest.SolveQuizRequest
import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizRepository
import javax.inject.Inject
import kotlin.random.Random.Default.nextBoolean

class QuizRepositoryImpl(private val quiz: Quiz, private val api: QuizApi) : QuizRepository {
    class Factory @Inject constructor(private val api: QuizApi) : QuizRepository.Factory {
        override fun invoke(p1: Quiz) = QuizRepositoryImpl(p1, api)
    }

    override suspend fun register(rightAnswers: Map<Int, Iterable<Int>>): Result<Unit> {
        val body = RegisterQuizRequest(quiz.toDto(), rightAnswers)
        return api.register(body)
    }

    override suspend fun solve(answers: Map<String, Iterable<String>>): Result<List<Pair<Question, Boolean>>> {
        val body = SolveQuizRequest(quiz.id, answers)
        // FIXME !!!
        val right = quiz.questions.associate { Pair(it.id, nextBoolean()) }
        return api.solve(body).map {
            quiz.questions.mapNotNull { question ->
                right[question.id]?.let { question to it }
            }
        }
    }
}