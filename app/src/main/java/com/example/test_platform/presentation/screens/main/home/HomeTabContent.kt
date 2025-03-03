package com.example.test_platform.presentation.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.presentation.components.avatar.UserAvatar
import com.example.test_platform.presentation.components.avatar.uuidIndex
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.theme.QuizTheme
import com.example.test_platform.presentation.theme.color

private val topShape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
private val bottomShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
val cardShape = RoundedCornerShape(16.dp)

@Composable
fun HomeTabContent(state: HomeTab.State, onAction: (HomeTab.Action) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .padding(horizontal = 8.dp)
    ) {
        TopPanel(
            modifier = Modifier.weight(2f),
            state = state,
            onAction = onAction
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomPanel(
            modifier = Modifier.weight(2f),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun BottomPanel(
    modifier: Modifier,
    state: HomeTab.State,
    onAction: (HomeTab.Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(bottomShape)
            .background(Color.White, bottomShape)
            .navigationBarsPadding()
            .screenPadding()
    ) {

    }
}

@Composable
private fun TopPanel(
    modifier: Modifier,
    state: HomeTab.State,
    onAction: (HomeTab.Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(topShape)
            .background(Color.White, topShape)
            .statusBarsPadding()
            .screenPadding()
    ) {
        val user = state.user
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            UserAvatar(
                modifier = Modifier.size(56.dp).shadow(elevation = 10.dp, shape = CircleShape),
                userId = user.id,
                url = user.avatar,
            )
        }
    }
}

@Composable
private fun AllQuizCard(
    modifier: Modifier = Modifier,
    quiz: Quiz,
    onClick: (Quiz) -> Unit
) {
    val index = quiz.id.uuidIndex()
    Column(
        modifier = Modifier
            .clip(cardShape)
            .background(QuizTheme.color(index))
            .padding(16.dp)
    ) {
        Row {
            Badge { Text(text = quiz.theme, color = Color.Black, fontWeight = FontWeight.Bold) }
            Spacer(modifier = Modifier.width(8.dp))
            Badge { Text(text = "${quiz.durationMinutes} min", color = Color.White) }
        }
    }
}

@Composable
@NonRestartableComposable
private fun Badge(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        content = content
    )
}