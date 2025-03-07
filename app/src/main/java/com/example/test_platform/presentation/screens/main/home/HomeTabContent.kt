package com.example.test_platform.presentation.screens.main.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.components.AuthoredQuizCard
import com.example.test_platform.presentation.components.OwnQuizCard
import com.example.test_platform.presentation.components.StubState
import com.example.test_platform.presentation.components.avatar.UserAvatar
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.LocalBottomBarHeight
import com.example.test_platform.presentation.screens.main.profile.ProfileTab
import com.example.test_platform.presentation.screens.main.quizzes.QuizzesTab
import com.example.test_platform.presentation.screens.quiz.solve.QuizSolveScreen
import com.example.test_platform.presentation.theme.QuizTheme
import kotlin.math.absoluteValue

val topShape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
val bottomShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
val cardShape = RoundedCornerShape(16.dp)

@Composable
fun HomeTabContent(state: HomeTab.State, onAction: (HomeTab.Action) -> Unit) {
    var isLandscape by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration.orientation) {
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    LaunchedEffect(Unit) {
        onAction(HomeTab.Action.RefreshAll(true))
        onAction(HomeTab.Action.RefreshAll(true))
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizTheme.cream)
                .padding(horizontal = 8.dp)
                .padding(bottom = LocalBottomBarHeight.current)
                .statusBarsPadding()
        ) {
            TopPanel(
                modifier = Modifier.weight(1f),
                state = state,
                onAction = onAction,
                shape = bottomShape
            )
            Spacer(modifier = Modifier.width(8.dp))
            BottomPanel(
                modifier = Modifier.weight(1f),
                state = state,
                onAction = onAction,
                shape = bottomShape
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizTheme.cream)
                .padding(horizontal = 8.dp)
        ) {
            TopPanel(
                modifier = Modifier.weight(1f),
                state = state,
                onAction = onAction,
                shape = topShape
            )
            Spacer(modifier = Modifier.height(24.dp))
            BottomPanel(
                modifier = Modifier.weight(1.3f),
                state = state,
                onAction = onAction,
                shape = bottomShape
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BottomPanel(
    modifier: Modifier = Modifier,
    state: HomeTab.State,
    shape: Shape,
    onAction: (HomeTab.Action) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.White, bottomShape)
            .navigationBarsPadding()
            .screenPadding(),
    ) {
        StubState(
            modifier = Modifier.fillMaxSize(),
            stub = state.ownStub,
            onRetry = { onAction(HomeTab.Action.RefreshOwn(false)) },
        ) {
            val navigator = LocalRootNavigator.current
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = LocalBottomBarHeight.current)
            ) {
                stickyHeader("header") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Your Quizzes",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        val tabNavigator = LocalTabNavigator.current
                        Text(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    tabNavigator.current = QuizzesTab
                                },
                            text = "See all",
                            color = QuizTheme.violet,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                items(
                    items = state.ownQuizzes,
                    key = { it.id },
                ) { quiz ->
                    OwnQuizCard(
                        modifier = Modifier.fillMaxWidth(),
                        quiz = quiz,
                        onClick = { navigator.push(QuizSolveScreen(quiz)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopPanel(
    modifier: Modifier = Modifier,
    state: HomeTab.State,
    shape: Shape,
    onAction: (HomeTab.Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.White, topShape)
            .statusBarsPadding()
    ) {
        val user = state.user
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .screenPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            val tabNavigator = LocalTabNavigator.current
            UserAvatar(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(elevation = 10.dp, shape = CircleShape),
                userId = user.id,
                url = user.avatar,
                onClick = { tabNavigator.current = ProfileTab }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.screenPadding(),
            text = "Quizzes",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        StubState(
            modifier = Modifier.fillMaxSize().screenPadding(),
            stub = state.allStub,
            onRetry = { onAction(HomeTab.Action.RefreshAll(false)) },
        ) {
            val quizzes = state.allQuizzes
            val pagerState = rememberPagerState { quizzes.size }
            val navigator = LocalRootNavigator.current
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                key = { quizzes[it].id },
                verticalAlignment = Alignment.CenterVertically,
                pageSpacing = 4.dp,
                contentPadding = PaddingValues(horizontal = 32.dp),
                state = pagerState,
            ) { page ->
                val quiz = quizzes[page]
                AuthoredQuizCard(
                    modifier = Modifier
                        .width(350.dp)
                        .graphicsLayer {
                            val diff = (pagerState.currentPage - page)
                            val pageOffset = (diff + pagerState.currentPageOffsetFraction).absoluteValue

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                            scaleY = lerp(
                                start = 0.75f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    quiz = quiz,
                    onClick = { navigator.push(QuizSolveScreen(quiz)) }
                )
            }
        }
    }
}