package com.example.test_platform.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.presentation.components.avatar.UserAvatar
import com.example.test_platform.presentation.components.avatar.uuidIndex
import com.example.test_platform.presentation.screens.main.home.cardShape
import com.example.test_platform.presentation.theme.QuizTheme
import com.example.test_platform.presentation.theme.color

@Composable
fun QuizCard(
    modifier: Modifier = Modifier,
    quiz: Quiz,
    bottom: @Composable ColumnScope.() -> Unit,
    onClick: () -> Unit
) {
    val index = quiz.id.uuidIndex()
    Column(
        modifier = modifier
            .clip(cardShape)
            .background(QuizTheme.color(index))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row {
            Badge { Text(text = quiz.theme, color = Color(0xFFC9353F), fontWeight = FontWeight.Bold) }
            Spacer(modifier = Modifier.width(8.dp))
            Badge { Text(text = "${quiz.durationMinutes} min", color = Color.Black) }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = quiz.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${quiz.questions.size} Questions",
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        bottom()
    }
}

@Composable
fun AuthoredQuizCard(
    modifier: Modifier = Modifier,
    quiz: Quiz,
    onClick: () -> Unit
) {
    QuizCard(
        modifier = modifier,
        quiz = quiz,
        onClick = onClick,
        bottom = {
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                UserAvatar(
                    modifier = Modifier.size(40.dp),
                    userId = quiz.author.id,
                    url = quiz.author.avatar
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.height(48.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Shared By",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = quiz.author.name,
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    )
}

@Composable
fun OwnQuizCard(
    modifier: Modifier = Modifier,
    quiz: Quiz,
    onClick: () -> Unit
) {
    QuizCard(
        modifier = modifier,
        quiz = quiz,
        onClick = onClick,
        bottom = {
            val solvers = quiz.lastSolvers
            val solvedCount = quiz.solvedCount

            if (solvers.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box {
                        solvers.fastForEachIndexed { i, user ->
                            key(user.id) {
                                UserAvatar(
                                    modifier = Modifier
                                        .padding(start = 20.dp * i)
                                        .size(40.dp),
                                    userId = user.id,
                                    url = user.avatar,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "+ $solvedCount People join",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    )
}

@Composable
@NonRestartableComposable
fun Badge(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        content = content
    )
}