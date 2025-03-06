package com.example.test_platform.presentation.screens.quiz.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.components.AppBar
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.home.bottomShape
import com.example.test_platform.presentation.screens.quiz.create.sub.tfShape
import com.example.test_platform.presentation.theme.QuizTheme
import kotlinx.coroutines.launch

private val decimalKeyboardOptions = KeyboardOptions.Default.copy(
    keyboardType = KeyboardType.Decimal
)

@Composable
fun QuizCreateScreenContent(
    state: QuizCreateScreen.State,
    onAction: (QuizCreateScreen.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .systemBarsPadding()
            .padding(8.dp)
            .imePadding(),
    ) {
        val navigator = LocalRootNavigator.current
        AppBar(
            modifier = Modifier
                .fillMaxWidth()
                .screenPadding(),
            title = "Create Quiz",
            isBackVisible = true,
            back = { navigator.pop() },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(bottomShape)
                .background(Color.White, bottomShape)
                .padding(8.dp)
        ) {
            AnimatedContent(
                targetState = state,
                contentKey = { it.javaClass },
                transitionSpec = {
                    slideIntoContainer(SlideDirection.Start) togetherWith
                            slideOutOfContainer(SlideDirection.Start)
                }
            ) { target ->
                when (target) {
                    is QuizCreateScreen.State.Questions -> QuestionsContent(target, onAction)
                    is QuizCreateScreen.State.QuizSettings -> QuizSettingsContent(target, onAction)
                }
            }
        }
    }
}

@Composable
private fun QuizSettingsContent(
    state: QuizCreateScreen.State.QuizSettings,
    onAction: (QuizCreateScreen.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = state.title,
            onValueChange = { onAction(QuizCreateScreen.Action.Title(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Name of the Quiz",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            shape = tfShape,
        )

        OutlinedTextField(
            value = state.theme,
            onValueChange = { onAction(QuizCreateScreen.Action.Theme(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Quiz theme",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            shape = tfShape,
        )

        val count = state.questionsCount
        OutlinedTextField(
            value = count?.toString().orEmpty(),
            onValueChange = { onAction(QuizCreateScreen.Action.Count(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Count of questions",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            shape = tfShape,
            isError = count == null || count > 20 || count <= 0,
            keyboardOptions = decimalKeyboardOptions,
        )

        val duration = state.duration
        OutlinedTextField(
            value = duration?.toString().orEmpty(),
            onValueChange = { onAction(QuizCreateScreen.Action.Duration(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Quiz duration",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            shape = tfShape,
            isError = duration == null || duration > 30 || duration <= 0,
            keyboardOptions = decimalKeyboardOptions,
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = { onAction(QuizCreateScreen.Action.Next) },
            content = { Text(text = "Continue", color = Color.White) },
            shape = RoundedCornerShape(8.dp),
            enabled = state.fulled,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )
    }
}

@Composable
private fun QuestionsContent(
    state: QuizCreateScreen.State.Questions,
    onAction: (QuizCreateScreen.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val items = state.items
        val pagerState = rememberPagerState { items.size }
        val scope = rememberCoroutineScope()

        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(visible = pagerState.currentPage != 0) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                                val target = (pagerState.currentPage - 1).coerceIn(items.indices)
                                pagerState.animateScrollToPage(target)
                            }
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }

            Text(
                text = "Question ",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            AnimatedContent(
                targetState = pagerState.currentPage,
                transitionSpec = {
                    val spec = if (targetState > initialState) {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    }
                    spec using SizeTransform(clip = false)
                }
            ) {
                Text(
                    text = "${it + 1}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            repeat(pagerState.pageCount) { i ->
                key(i) {
                    val selected = pagerState.currentPage == i
                    val color by animateColorAsState(
                        targetValue = if (selected) QuizTheme.blue else QuizTheme.gray
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(color = color, shape = CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalPager(
            modifier = Modifier.weight(1f),
            key = { items[it].key },
            verticalAlignment = Alignment.CenterVertically,
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { items[it].Content() }
        )

        val buttonActive = items[pagerState.currentPage].state.fulled
        val isLast = pagerState.currentPage == items.lastIndex
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                if (isLast) {
                    onAction(QuizCreateScreen.Action.Complete)
                } else {
                    scope.launch {
                        val target = (pagerState.currentPage + 1).coerceIn(items.indices)
                        pagerState.animateScrollToPage(target)
                    }
                }
            },
            content = {
                AnimatedContent(
                    targetState = state.registerInProgress,
                    transitionSpec = {
                        fadeIn(tween(400)) togetherWith fadeOut(tween(200))
                    }
                ) { loading ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.White,
                            )
                        } else {
                            Text(text = if (isLast) "Register" else "Continue", color = Color.White)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            enabled = buttonActive,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )
    }
}