package com.example.test_platform.data.quiz.source

import com.example.test_platform.data.dto.QuizDto
import com.example.test_platform.data.dto.toDomain
import com.example.test_platform.data.paging.StateSource
import com.example.test_platform.data.quiz.rest.QuizApi
import com.example.test_platform.domain.paging.source.PageableSource
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizSource
import java.time.Instant
import javax.inject.Inject

class AllSource private constructor(
    private val limit: Int,
    private val quizApi: QuizApi
) : QuizSource, PageableSource<Instant, Quiz> by StateSource(
    getKey = Quiz::id,
    doFetch = { cursor ->
        quizApi
            .quizzes(limit, cursor.toString())
            .map(List<QuizDto>::toDomain)
    }
) {
    class Factory @Inject constructor(private val api: QuizApi) : QuizSource.Factory {
        override fun invoke(p1: Int) = AllSource(p1, api)
    }
}