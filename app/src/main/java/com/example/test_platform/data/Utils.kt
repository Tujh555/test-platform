package com.example.test_platform.data

import com.example.test_platform.data.store.Store
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds

fun Result<*>.map() = map {  }

suspend fun <T> Store<T>.get(): T? = withTimeoutOrNull(0.5.seconds) {
    data.filterNotNull().first()
}