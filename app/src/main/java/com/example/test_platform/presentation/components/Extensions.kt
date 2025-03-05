package com.example.test_platform.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

fun LazyListState.lastVisibleItemIndex() =
    snapshotFlow { layoutInfo.visibleItemsInfo }
        .mapNotNull { it.lastOrNull()?.index }
        .distinctUntilChanged()