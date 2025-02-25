package com.example.test_platform.presentation.theme

import com.example.test_platform.domain.user.User
import java.lang.Long.parseLong

fun String.uuidToIndex(): Long {
    if (length < 8) return 0
    return runCatching { parseLong(this, 0, 8, 36) }
        .getOrDefault(0)
}

fun User.fallbackAvatar(): String {
    val index = id.uuidToIndex() % 50 + 1

    return "file:///android_asset/avatar_$index.webp"
}