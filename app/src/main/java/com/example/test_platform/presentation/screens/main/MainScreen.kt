package com.example.test_platform.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.test_platform.presentation.components.tabs.BottomNavigationBar
import com.example.test_platform.presentation.screens.main.home.HomeTab
import com.example.test_platform.presentation.screens.main.profile.ProfileTab
import com.example.test_platform.presentation.screens.main.quizzes.QuizzesTab
import com.example.test_platform.presentation.screens.main.search.SearchTab

class MainScreen : Screen {
    private val tabs = listOf(HomeTab(), QuizzesTab(), SearchTab(), ProfileTab())

    @Composable
    override fun Content() {
        TabNavigator(tabs.first()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CurrentTab()
                BottomNavigationBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    tabs = tabs,
                    onFabClick = {  }
                )
            }
        }
    }
}