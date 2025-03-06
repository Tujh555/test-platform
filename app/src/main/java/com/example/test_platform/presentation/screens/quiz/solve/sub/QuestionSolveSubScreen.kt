package com.example.test_platform.presentation.screens.quiz.solve.sub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.test_platform.domain.test.Question
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.screens.quiz.solve.models.SolvingQuestion

private class QuestionSolveSubScreen(question: Question) : SubScreen<SolvingQuestion> {
    override var state: SolvingQuestion by mutableStateOf(SolvingQuestion(question))
        private set

    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}

@Suppress("FunctionName")
fun QuestionSolveScreen(question: Question): SubScreen<SolvingQuestion> =
    QuestionSolveSubScreen(question)