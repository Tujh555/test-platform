package com.example.test_platform.presentation.base

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

@Stable
interface TabComponent<A, S, E> : Tab, ScreenComponent<A, S, E> {
    override val options @Composable get() = emptyOptions

    val title: String
    val icons: IconPair

    companion object {
        private val emptyOptions = TabOptions(0u, "", null)
    }
}

interface StateTabComponent<A, S> : TabComponent<A, S, Nothing> {
    @Composable
    override fun Event(event: Nothing) = Unit
}

@Immutable
data class IconPair(@DrawableRes val selected: Int, @DrawableRes val unselected: Int)