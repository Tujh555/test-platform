package com.example.test_platform

import kotlinx.coroutines.delay

const val DELAY_DEFAULT = 600L
const val DELAY_SHORT = 300L
const val DELAY_LONG = 1000L

suspend inline fun <T> withMinDelay(delay: Long = DELAY_DEFAULT, block: () -> T): T {
    val startTime = System.currentTimeMillis()
    val result = block()
    val elapsedTime = System.currentTimeMillis() - startTime
    val additionalDelay = (delay - elapsedTime).coerceIn(0, delay)
    delay(additionalDelay)
    return result
}