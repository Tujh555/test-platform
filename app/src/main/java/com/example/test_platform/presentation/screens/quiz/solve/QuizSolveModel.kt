package com.example.test_platform.presentation.screens.quiz.solve

import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizRepository
import com.example.test_platform.presentation.base.EventEmitter
import com.example.test_platform.presentation.base.Model
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.io
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.error.ErrorHandler
import com.example.test_platform.presentation.screens.quiz.solve.models.SolvingQuestion
import com.example.test_platform.presentation.screens.quiz.solve.sub.QuestionSolveScreen
import com.example.test_platform.timer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class QuizSolveModel @AssistedInject constructor(
    @Assisted private val quiz: Quiz,
    repository: QuizRepository.Factory,
    private val errorHandler: ErrorHandler
) : Model<QuizSolveScreen.Action, QuizSolveScreen.State, QuizSolveScreen.Event>,
    StateHolder<QuizSolveScreen.State> by StateHolder(quiz.solveState()),
    EventEmitter<QuizSolveScreen.Event> by EventEmitter() {

    @AssistedFactory
    interface Factory : ScreenModelFactory, (Quiz) -> QuizSolveModel
    private val repository = repository(quiz)

    init {
        launchTimer()
    }

    override fun onAction(action: QuizSolveScreen.Action) {
        when (action) {
            QuizSolveScreen.Action.Finish -> finish()
        }
    }

    private fun finish() {
        update { state -> state.copy(finishInProgress = true) }
        val solvingQuestions = state.value.solveScreens.map(SubScreen<SolvingQuestion>::state)
        val answersMap = solvingQuestions.associate { it.underlying.id to it.markedAnswers }

        screenModelScope.io {
            repository
                .solve(answersMap)
                .onSuccess { result ->
                    emit(QuizSolveScreen.Event.ResultScreen(quiz.title, result))
                }
                .onFailure {
                    errorHandler.handle("Something went wrong")
                    update { state -> state.copy(finishInProgress = false) }
                }
        }
    }

    private fun launchTimer() {
        timer(quiz.durationMinutes.minutes, 1.seconds)
            .onEach { remaining -> update { state -> state.copy(remainingTime = remaining) } }
            .onCompletion {
                if (state.value.finishInProgress.not()) {
                    finish()
                }
            }
            .launchIn(screenModelScope)
    }
}

private fun Quiz.solveState() = QuizSolveScreen.State(
    quiz = this,
    solveScreens = questions.map(::QuestionSolveScreen),
)