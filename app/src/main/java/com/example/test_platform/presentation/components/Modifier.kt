package com.example.test_platform.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.screenPadding() = padding(horizontal = 16.dp)

inline fun Modifier.applyIf(condition: () -> Boolean, block: Modifier.() -> Modifier): Modifier {
    if (condition()) {
        return this.block()
    }

    return this
}

fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier) =
    applyIf({ condition }, block)

fun <T> Modifier.applyNotNull(item: T?, block: Modifier.(T) -> Modifier): Modifier {
    if (item == null) {
        return this
    }

    return this.block(item)
}