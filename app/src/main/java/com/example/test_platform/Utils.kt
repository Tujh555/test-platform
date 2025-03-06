package com.example.test_platform

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

suspend inline fun <T> withMinDelay(delay: Long = 600, block: () -> T): T {
    val startTime = System.currentTimeMillis()
    val result = block()
    val elapsedTime = System.currentTimeMillis() - startTime
    val additionalDelay = (delay - elapsedTime).coerceIn(0, delay)
    delay(additionalDelay)
    return result
}

fun String?.toInstantOrNow(): Instant =
    runCatching { Instant.parse(this!!) }.getOrDefault(Instant.now())

fun timer(duration: Duration, interval: Duration): Flow<Duration> {
    require(interval < duration) { "Invalid interval $interval for duration $duration" }

    return flow {
        var remaining = duration

        while (currentCoroutineContext().isActive && remaining > 0.seconds) {
            emit(remaining)
            remaining -= interval
            delay(interval)
        }
        emit(remaining)
    }
}