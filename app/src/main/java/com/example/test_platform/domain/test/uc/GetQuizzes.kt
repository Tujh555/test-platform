package com.example.test_platform.domain.test.uc

import com.example.test_platform.domain.paging.paginator.statePaginator
import com.example.test_platform.domain.paging.source.PageableSourcePager
import com.example.test_platform.domain.paging.source.PagedElements
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import javax.inject.Inject

class GetQuizzes @Inject constructor(
    private val all: QuizSource.Factory,
    private val own: QuizSource.OwnFactory,
    private val search: QuizSource.SearchFactory
) {
    private val limit = 10

    fun all(minDelay: Long = 0) = fromSource(minDelay, all)

    fun own(minDelay: Long = 0) = fromSource(minDelay, own)

    fun search(minDelay: Long = 0, searchQuery: Flow<String>) = object : PageableSourcePager<Quiz> {
        private var _underlying: PageableSourcePager<Quiz>? = null

        override fun paginate(position: Flow<Int>): Flow<PagedElements<Quiz>> = channelFlow {
            searchQuery.collectLatest { query ->
                val underlying = fromSource(minDelay) { limit ->
                    search(query, limit)
                }
                _underlying = underlying
                underlying.paginate(position).collect(::send)
            }
        }

        override suspend fun refresh() {
            _underlying?.refresh()
        }
    }

    private fun fromSource(minDelay: Long, factory: (Int) -> QuizSource): PageableSourcePager<Quiz> {
        val source = factory(limit)
        val paginator = statePaginator(Instant.now(), limit, source, Quiz::createdAt)
        return PageableSourcePager(source, paginator, minDelay)
    }
}