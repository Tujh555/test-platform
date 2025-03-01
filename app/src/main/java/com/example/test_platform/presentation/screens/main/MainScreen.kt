package com.example.test_platform.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.test_platform.presentation.components.tabs.BottomNavigationBar
import com.example.test_platform.presentation.screens.main.home.HomeTab
import com.example.test_platform.presentation.screens.main.profile.ProfileTab
import com.example.test_platform.presentation.screens.main.quizzes.QuizzesTab
import com.example.test_platform.presentation.screens.main.search.SearchTab

val LocalBottomBarHeight = compositionLocalOf { 0.dp }

class MainScreen : Screen {
    private val tabs = listOf(HomeTab(), QuizzesTab(), SearchTab(), ProfileTab())

    @Composable
    override fun Content() {
        TabNavigator(tabs.first()) {
            Box(modifier = Modifier.fillMaxSize()) {
                var bottomBarHeight by remember { mutableStateOf(0.dp) }
                CompositionLocalProvider(LocalBottomBarHeight provides bottomBarHeight) {
                    CurrentTab()
                }
                val density = LocalDensity.current
                BottomNavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .onSizeChanged { size ->
                            val height = with(density) { size.height.toDp() }
                            if (bottomBarHeight != height) {
                                bottomBarHeight = height
                            }
                        },
                    tabs = tabs,
                    onFabClick = {  }
                )
            }
        }
    }
}