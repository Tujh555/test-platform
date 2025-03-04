package com.example.test_platform.presentation.screens.main.quizzes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.R
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.TabComponent

object QuizzesTab : TabComponent<QuizzesTab.Action, QuizzesTab.State> {

    @Immutable
    data class State(val stub: String = "")

    @Immutable
    sealed interface Action

    override val title: String = "Quizzes"
    override val icons: IconPair = IconPair(
        selected = R.drawable.ic_quizes_selected,
        unselected = R.drawable.ic_quizes_unselected
    )

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Quizzes")
        }
    }

    @Composable
    override fun model(): QuizzesTabModel = getScreenModel()

    private fun readResolve(): Any = QuizzesTab
}