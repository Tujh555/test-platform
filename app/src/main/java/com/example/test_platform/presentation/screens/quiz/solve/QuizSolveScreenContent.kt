package com.example.test_platform.presentation.screens.quiz.solve

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.test_platform.domain.test.Answer
import com.example.test_platform.domain.test.Question
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.user.User
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.components.Badge
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.home.bottomShape
import com.example.test_platform.presentation.screens.quiz.solve.sub.QuestionSolveScreen
import com.example.test_platform.presentation.theme.QuizTheme
import java.time.Instant
import java.util.UUID
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizSolveScreenContent(
    state: QuizSolveScreen.State,
    onAction: (QuizSolveScreen.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .systemBarsPadding()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
    ) {
        val navigator = kotlin.runCatching { LocalRootNavigator.current }.getOrNull()
        Row(
            modifier = Modifier.fillMaxWidth().screenPadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = state.quiz.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val shape = RoundedCornerShape(12.dp)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape)
                    .background(Color.White, shape)
                    .border(0.5.dp, QuizTheme.border, shape)
                    .clickable { navigator?.pop() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            modifier = Modifier.screenPadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.shadow(8.dp, shape = CircleShape)) {
                Badge { Text(text = state.quiz.theme, color = Color.Black) }
            }
            Box(modifier = Modifier.shadow(8.dp, shape = CircleShape).animateContentSize()) {
                Badge {
                    val color by animateColorAsState(
                        targetValue = if (state.remainingTime <= 20.seconds) {
                            QuizTheme.red
                        } else {
                            Color.Black
                        }
                    )
                    Text(text = "${state.remainingTime}", color = color, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(bottomShape)
                .background(Color.White, bottomShape)
        ) {
            val items = state.solveScreens
            val pagerState = rememberPagerState { items.size }

            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
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
                    pageSpacing = 24.dp,
                    userScrollEnabled = state.finishInProgress.not(),
                    pageContent = {
                        Box(
                            modifier = Modifier.padding(8.dp).graphicsLayer {
                                val diff = (pagerState.currentPage - it)
                                val pageOffset =
                                    (diff + pagerState.currentPageOffsetFraction).absoluteValue

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                        ) {
                            items[it].Content()
                        }
                    }
                )

                FinishButton(
                    visible = pagerState.currentPage == items.lastIndex || state.finishInProgress,
                    isLoading = state.finishInProgress,
                    onClick = { onAction(QuizSolveScreen.Action.Finish) }
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.FinishButton(
    modifier: Modifier = Modifier,
    visible: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible
    ) {
        Button(
            modifier = Modifier
                .padding(bottom = 24.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .height(48.dp),
            onClick = onClick,
            content = {
                AnimatedContent(
                    targetState = isLoading,
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
                            Text(text = "Finish", color = Color.White)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )
    }
}

val LocalInteractionAllowed = compositionLocalOf { true }

fun lorem(count: Int) = LoremIpsum(count).values.joinToString(separator = "")
fun randomId() = UUID.randomUUID().toString()

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        val quiz = Quiz(
            id = randomId(),
            title = "Just preview",
            durationMinutes = 1,
            questions = listOf(
                Question(
                    id = randomId(),
                    text = lorem(20),
                    variants = listOf(
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2))
                    ),
                    type = Question.Type.Multiple
                ),
                Question(
                    id = randomId(),
                    text = lorem(2),
                    variants = listOf(
                        Answer(randomId(), lorem(4)),
                        Answer(randomId(), lorem(4)),
                        Answer(randomId(), lorem(4))
                    ),
                    type = Question.Type.Single
                ),
                Question(
                    id = randomId(),
                    text = lorem(20),
                    variants = listOf(
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2))
                    ),
                    type = Question.Type.Multiple
                ),
                Question(
                    id = randomId(),
                    text = lorem(20),
                    variants = listOf(
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                        Answer(randomId(), lorem(2)),
                    ),
                    type = Question.Type.Multiple
                )
            ),
            author = User(id = randomId(), avatar = null, name = ""),
            solvedCount = 0,
            lastSolvers = listOf(),
            createdAt = Instant.now(),
            theme = "Preview theme"
        )

        val state = QuizSolveScreen.State(
            quiz = quiz,
            solveScreens = quiz.questions.map(::QuestionSolveScreen),
            remainingTime = 10.seconds
        )

        QuizSolveScreenContent(state) { }
    }
}