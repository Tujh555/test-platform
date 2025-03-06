package com.example.test_platform.presentation.screens.quiz.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.base.ScreenComponent
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.screens.quiz.create.models.RawQuestion

class QuizCreateScreen :
    ScreenComponent<QuizCreateScreen.Action, QuizCreateScreen.State, QuizCreateScreen.Event> {
    @Immutable
    sealed interface State {
        data class QuizSettings(
            val title: String = "",
            val theme: String = "",
            val questionsCount: Int? = 5,
            val duration: Int? = 10,
        ) : State {
            val fulled = title.isNotBlank() &&
                    theme.isNotBlank() &&
                    questionsCount != null &&
                    questionsCount in (1..20) &&
                    duration != null &&
                    duration in (1..30)
        }

        data class Questions(
            val items: List<SubScreen<RawQuestion>>,
            val registerInProgress: Boolean = false
        ) : State
    }

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Title(val value: String) : Action

        @JvmInline
        value class Theme(val value: String) : Action

        @JvmInline
        value class Count(val value: String) : Action

        @JvmInline
        value class Duration(val value: String) : Action

        data object Next : Action

        data object Complete : Action
    }

    @Immutable
    sealed interface Event {
        class Close : Event
    }

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) =
        QuizCreateScreenContent(state, onAction)

    @Composable
    override fun Event(event: Event) {
        val navigator = LocalRootNavigator.current
        LaunchedEffect(event) {
            when (event) {
                is Event.Close -> navigator.pop()
            }
        }
    }

    @Composable
    override fun model(): QuizCreateModel = getScreenModel()
}