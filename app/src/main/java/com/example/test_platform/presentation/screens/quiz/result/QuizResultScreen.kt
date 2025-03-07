package com.example.test_platform.presentation.screens.quiz.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.test_platform.R
import com.example.test_platform.domain.test.Question
import com.example.test_platform.presentation.LocalRootNavigator
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.main.home.bottomShape
import com.example.test_platform.presentation.screens.quiz.solve.lorem
import com.example.test_platform.presentation.screens.quiz.solve.sub.variantShape
import com.example.test_platform.presentation.theme.QuizTheme
import java.util.UUID

class QuizResultScreen(
    private val quizName: String,
    private val result: List<Pair<Question, Boolean>>
) : Screen {
    private val rightCountText = buildString {
        append("Correct answers ")
        append(result.count { it.second })
        append("/")
        append(result.size)
    }
    private val successColor = Color(0xFF008000)

    @Composable
    override fun Content() {
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
                horizontalArrangement = Arrangement.End
            ) {
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

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White, bottomShape)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(150.dp).offset {
                            IntOffset(x = 0, y = ((-43).dp).roundToPx())
                        },
                        painter = painterResource(R.drawable.img_quiz_complete),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.screenPadding(),
                        text = quizName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.screenPadding(),
                        text = rightCountText,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().screenPadding(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = result, key = { it.first.id }) { (question, correct) ->
                            val (border, background) = if (correct) {
                                successColor to successColor.copy(alpha = 0.2f)
                            } else {
                                MaterialTheme.colorScheme.run { error to errorContainer }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(background, variantShape)
                                    .border(width = 1.dp, color = border, variantShape)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = question.text,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        val questions = listOf(
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(1),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(2),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(3),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(4),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(5),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(6),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(1),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(2),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(3),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(4),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(5),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(6),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(1),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(2),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(3),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(4),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(5),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(6),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(1),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(2),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(3),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(4),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(5),
                variants = listOf(),
                type = Question.Type.Single
            ) to false,
            Question(
                id = UUID.randomUUID().toString(),
                text = lorem(6),
                variants = listOf(),
                type = Question.Type.Single
            ) to true,
        )
        QuizResultScreen("Test quiz name", questions).Content()
    }
}