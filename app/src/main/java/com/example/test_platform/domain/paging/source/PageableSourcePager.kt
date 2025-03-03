package com.example.test_platform.domain.paging.source

import com.example.test_platform.domain.paging.paginator.LoadState
import kotlinx.coroutines.flow.Flow

interface PageableSourcePager<T : Any> {
    fun paginate(position: Flow<Int>): Flow<PagedElements<T>>

    suspend fun refresh()
}

data class PagedElements<T : Any>(
    val elements: List<T>,
    val loadState: LoadState,
)