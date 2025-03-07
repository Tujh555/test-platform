package com.example.test_platform.presentation.screens.main.search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import com.example.test_platform.R
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.StateTabComponent
import com.example.test_platform.presentation.components.Stub
import com.example.test_platform.presentation.components.lastVisibleItemIndex
import kotlinx.coroutines.flow.onStart

object SearchTab : StateTabComponent<SearchTab.Action, SearchTab.State> {
    @Immutable
    data class State(
        val query: String = "",
        val quizzes: List<Quiz> = emptyList(),
        val stub: Stub = Stub.Loading,
        val adding: Boolean = false,
        val listState: LazyListState = LazyListState()
    ) {
        val lastQuizIndex get() = listState.lastVisibleItemIndex().onStart { emit(0) }
    }

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Query(val value: String) : Action
        @JvmInline
        value class Refresh(val silent: Boolean) : Action
    }

    override val title: String = "Search"
    override val icons: IconPair = IconPair(
        selected = R.drawable.ic_search_selected,
        unselected = R.drawable.ic_search_unselected
    )

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) =
        SearchScreenContent(state, onAction)

    @Composable
    override fun model(): SearchTabModel = LocalRootNavigator.current.getNavigatorScreenModel()

    private fun readResolve(): Any = SearchTab
}