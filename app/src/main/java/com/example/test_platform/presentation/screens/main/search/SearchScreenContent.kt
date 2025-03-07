package com.example.test_platform.presentation.screens.main.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.example.test_platform.presentation.components.AuthoredQuizCard
import com.example.test_platform.presentation.components.StubState
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.LocalBottomBarHeight
import com.example.test_platform.presentation.screens.main.home.bottomShape
import com.example.test_platform.presentation.screens.quiz.solve.QuizSolveScreen
import com.example.test_platform.presentation.screens.signup.onCreamTextFieldColors
import com.example.test_platform.presentation.theme.QuizTheme

@Composable
fun SearchScreenContent(state: SearchTab.State, onAction: (SearchTab.Action) -> Unit) {

    LaunchedEffect(Unit) {
        onAction(SearchTab.Action.Refresh(true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .padding(horizontal = 8.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .screenPadding(),
            text = "Search",
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.query,
            onValueChange = { onAction(SearchTab.Action.Query(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .screenPadding(),
            placeholder = { Text(text = "Search quizzes") },
            colors = onCreamTextFieldColors(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                AnimatedContent(
                    targetState = state.query.isEmpty(),
                    transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
                ) { empty ->
                    if (empty) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = QuizTheme.blue
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .clickable { onAction(SearchTab.Action.Query("")) },
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(bottomShape)
                .background(Color.White, bottomShape)
                .navigationBarsPadding()
                .screenPadding(),
        ) {
            StubState(
                modifier = Modifier.fillMaxSize(),
                stub = state.stub,
                onRetry = { onAction(SearchTab.Action.Refresh(false)) }
            ) {
                val navigator = LocalRootNavigator.current
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        bottom = LocalBottomBarHeight.current + 20.dp,
                        top = 16.dp
                    ),
                    state = state.listState
                ) {
                    items(items = state.quizzes, key = { it.id }) { quiz ->
                        AuthoredQuizCard(
                            modifier = Modifier.fillMaxWidth(),
                            quiz = quiz,
                            onClick = { navigator.push(QuizSolveScreen(quiz)) }
                        )
                    }

                    if (state.adding) {
                        item("loader") {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
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