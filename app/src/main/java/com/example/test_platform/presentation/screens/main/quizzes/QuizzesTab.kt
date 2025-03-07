package com.example.test_platform.presentation.screens.main.quizzes

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

object QuizzesTab : StateTabComponent<QuizzesTab.Action, QuizzesTab.State> {

    @Immutable
    data class State(
        val quizzes: List<Quiz> = emptyList(),
        val stub: Stub = Stub.Loading,
        val listState: LazyListState = LazyListState(),
        val adding: Boolean = false,
    ) {
        val lastQuizIndex get() = listState.lastVisibleItemIndex().onStart { emit(0) }
    }

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Refresh(val silent: Boolean) : Action
    }

    override val title: String = "Quizzes"
    override val icons: IconPair = IconPair(
        selected = R.drawable.ic_quizes_selected,
        unselected = R.drawable.ic_quizes_unselected
    )

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) =
        QuizzesTabScreenContent(state, onAction)

    @Composable
    // TODO silent refersh
    override fun model(): QuizzesTabModel = LocalRootNavigator.current.getNavigatorScreenModel()

    private fun readResolve(): Any = QuizzesTab
}