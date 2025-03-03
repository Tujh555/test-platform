package com.example.test_platform.domain.test.repository

import com.example.test_platform.domain.paging.source.PageableSource
import com.example.test_platform.domain.test.Quiz
import java.time.Instant

interface QuizSource : PageableSource<Instant, Quiz> {
    interface Factory : (Int) -> QuizSource
    interface OwnFactory : (Int) -> QuizSource
    interface SearchFactory : (String, Int) -> QuizSource
}