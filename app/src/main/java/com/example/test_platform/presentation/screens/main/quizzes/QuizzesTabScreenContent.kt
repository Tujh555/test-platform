package com.example.test_platform.presentation.screens.main.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.components.OwnQuizCard
import com.example.test_platform.presentation.components.StubState
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.LocalBottomBarHeight
import com.example.test_platform.presentation.screens.main.home.bottomShape
import com.example.test_platform.presentation.screens.quiz.solve.QuizSolveScreen
import com.example.test_platform.presentation.theme.QuizTheme

@Composable
fun QuizzesTabScreenContent(state: QuizzesTab.State, onAction: (QuizzesTab.Action) -> Unit) {
    LaunchedEffect(Unit) {
        onAction(QuizzesTab.Action.Refresh(true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .statusBarsPadding()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().screenPadding(),
            text = "Your Quizzes",
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(bottomShape)
                .background(Color.White, bottomShape)
        ) {
            StubState(
                modifier = Modifier.fillMaxSize().screenPadding(),
                stub = state.stub,
                onRetry = { onAction(QuizzesTab.Action.Refresh(false)) }
            ) {
                val navigator = LocalRootNavigator.current
                LazyColumn(
                    modifier = Modifier.fillMaxSize().screenPadding(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        bottom = LocalBottomBarHeight.current + 20.dp,
                        top = 16.dp
                    ),
                    state = state.listState
                ) {
                    items(items = state.quizzes, key = { it.id }) { quiz ->
                        OwnQuizCard(
                            modifier = Modifier.fillMaxWidth().animateItem(),
                            quiz = quiz,
                            onClick = { navigator.push(QuizSolveScreen(quiz)) }
                        )
                    }

                    if (state.adding) {
                        item("loader") {
                            Box(
                                modifier = Modifier.fillMaxWidth().animateItem(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(56.dp),
                                    color = QuizTheme.violet
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}