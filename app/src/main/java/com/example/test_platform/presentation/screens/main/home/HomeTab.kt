package com.example.test_platform.presentation.screens.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.R
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.user.User
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.TabComponent

class HomeTab : TabComponent<HomeTab.Action, HomeTab.State> {
    @Immutable
    data class State(
        val user: User,
        val allQuizzes: List<Quiz> = emptyList(),
        val ownQuizzes: List<Quiz> = emptyList()
    )

    @Immutable
    sealed interface Action

    override val title: String = "Home"
    override val icons: IconPair = IconPair(
        selected = R.drawable.ic_home_selected,
        unselected = R.drawable.ic_home_unselected
    )

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) =
        HomeTabContent(state, onAction)

    @Composable
    override fun model(): HomeTabModel = getScreenModel()
}