package com.example.test_platform.presentation.components.avatar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import java.lang.Long.parseLong

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    userId: String,
    url: String? = null,
    shape: Shape = CircleShape,
) {
    val targetUrl = url ?: "file:///android_asset/avatar_${userId.uuidIndex() % 50 + 1}.webp"

    AsyncImage(
        modifier = modifier.clip(shape),
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(targetUrl)
            .crossfade(true).build(),
        contentDescription = null,
    )
}

fun String.uuidIndex(): Long {
    if (length < 8) return 0

    return runCatching { parseLong(this, 0, 8, 36) }
        .getOrDefault(0)
}