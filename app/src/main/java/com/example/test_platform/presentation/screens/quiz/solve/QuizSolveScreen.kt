package com.example.test_platform.presentation.screens.quiz.solve

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.base.ScreenComponent
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.screens.quiz.result.QuizResultScreen
import com.example.test_platform.presentation.screens.quiz.solve.models.SolvingQuestion
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class QuizSolveScreen(
    private val quiz: Quiz
) : ScreenComponent<QuizSolveScreen.Action, QuizSolveScreen.State, QuizSolveScreen.Event> {
    @Immutable
    data class State(
        val quiz: Quiz,
        val solveScreens: List<SubScreen<SolvingQuestion>>,
        val remainingTime: Duration = quiz.durationMinutes.minutes,
        val finishInProgress: Boolean = false
    )

    @Immutable
    sealed interface Action {
        data object Finish : Action
    }

    @Immutable
    sealed interface Event {
        class ResultScreen(val quizName: String, val result: List<Pair<Question, Boolean>>) : Event
    }

    @Composable
    override fun Content(state: State, onAction: (Action) -> Unit) {
        CompositionLocalProvider(
            LocalInteractionAllowed provides state.finishInProgress.not()
        ) {
            QuizSolveScreenContent(state, onAction)
        }
    }

    @Composable
    override fun Event(event: Event) {
        val navigator = LocalRootNavigator.current
        LaunchedEffect(event) {
            when (event) {
                is Event.ResultScreen -> {
                    val screen = QuizResultScreen(event.quizName, event.result)
                    navigator.replace(screen)
                }
            }
        }
    }

    @Composable
    override fun model() = getScreenModel<QuizSolveModel, QuizSolveModel.Factory> { it(quiz) }
}