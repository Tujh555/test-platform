package com.example.test_platform.data.paging

import com.example.test_platform.domain.paging.source.PageableSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class StateSource<K : Any, T : Any>(
    private val getKey: (T) -> Any,
    private val doFetch: suspend (K) -> Result<List<T>>
) : PageableSource<K, T> {
    private val items = MutableStateFlow(emptyList<T>())

    override fun get(): Flow<List<T>> = items

    override suspend fun resetTo(key: K) = doFetch(key).onSuccess { data ->
        items.value = data
    }

    override suspend fun fetch(key: K) = doFetch(key).onSuccess { data ->
        items.update { (it + data).distinctBy(getKey) }
    }

}