package com.example.test_platform.presentation.screens.quiz.create

import androidx.collection.ArrayMap
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.repository.QuizRepository
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.presentation.base.EventEmitter
import com.example.test_platform.presentation.base.Model
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.io
import com.example.test_platform.presentation.error.ErrorHandler
import com.example.test_platform.presentation.screens.quiz.create.models.toDomain
import com.example.test_platform.presentation.screens.quiz.create.sub.QuestionCreateScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import java.time.Instant
import javax.inject.Inject

class QuizCreateModel @Inject constructor(
    private val errorHandler: ErrorHandler,
    private val repository: QuizRepository.Factory,
    private val reactiveUser: ReactiveUser
) : Model<QuizCreateScreen.Action, QuizCreateScreen.State, QuizCreateScreen.Event>,
    StateHolder<QuizCreateScreen.State> by StateHolder(QuizCreateScreen.State.QuizSettings()),
    EventEmitter<QuizCreateScreen.Event> by EventEmitter() {

    private var settings = QuizCreateScreen.State.QuizSettings()
    private val user = screenModelScope.async { reactiveUser.filterNotNull().first() }

    override fun onAction(action: QuizCreateScreen.Action) {
        when (action) {
            is QuizCreateScreen.Action.Count -> count(action.value)
            is QuizCreateScreen.Action.Duration -> duration(action.value)
            QuizCreateScreen.Action.Next -> next()
            QuizCreateScreen.Action.Complete -> complete()
            is QuizCreateScreen.Action.Theme -> theme(action.value)
            is QuizCreateScreen.Action.Title -> title(action.value)
        }
    }

    private fun updateSettings(
        block: (QuizCreateScreen.State.QuizSettings) -> QuizCreateScreen.State.QuizSettings
    ) {
        update { state ->
            when (state) {
                is QuizCreateScreen.State.Questions -> state
                is QuizCreateScreen.State.QuizSettings -> block(state)
            }
        }
    }

    private fun updateQuestions(
        block: (QuizCreateScreen.State.Questions) -> QuizCreateScreen.State.Questions
    ) {
        update { state ->
            when (state) {
                is QuizCreateScreen.State.Questions -> block(state)
                is QuizCreateScreen.State.QuizSettings -> state
            }
        }
    }

    private fun complete() {
        val questionsState = state.value
        if (questionsState !is QuizCreateScreen.State.Questions) {
            return
        }
        updateQuestions { state -> state.copy(registerInProgress = true) }

        val rightAnswers = ArrayMap<Int, List<Int>>()
        val questions = mutableListOf<Question>()
        questionsState.items.forEachIndexed { i, screen ->
            val rawQuestion = screen.state
            questions.add(rawQuestion.toDomain())
            rightAnswers[i] = rawQuestion.answers.mapIndexedNotNull { index, answer ->
                if (answer.marked) index else null
            }
        }

        screenModelScope.io {
            val quiz = Quiz(
                id = "",
                title = settings.title,
                durationMinutes = settings.duration ?: 5,
                questions = questions,
                author = user.await(),
                solvedCount = 0,
                lastSolvers = listOf(),
                createdAt = Instant.now(),
                theme = settings.theme
            )
            repository(quiz)
                .register(rightAnswers)
                .onSuccess { emit(QuizCreateScreen.Event.Close()) }
                .onFailure {
                    updateQuestions { state -> state.copy(registerInProgress = true) }
                    errorHandler.handle("Failed to register quiz")
                }
        }
    }

    private fun count(value: String) {
        updateSettings { state -> state.copy(questionsCount = value.toIntOrNull()) }
    }

    private fun duration(value: String) {
        updateSettings { state -> state.copy(duration = value.toIntOrNull()) }
    }

    private fun theme(value: String) {
        updateSettings { state -> state.copy(theme = value) }
    }

    private fun title(value: String) {
        updateSettings { state -> state.copy(title = value) }
    }

    private fun next() {
        val count = (state.value as? QuizCreateScreen.State.QuizSettings)
            ?.also { settings = it }
            ?.questionsCount
            ?.takeIf { it > 0 }

        if (count == null) {
            errorHandler.handle("Wrong count value")
            return
        }

        update { QuizCreateScreen.State.Questions(List(count, ::QuestionCreateScreen)) }
    }
}