package com.example.test_platform.presentation.screens.quiz.solve.sub

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.ScreenKey
import com.example.test_platform.domain.test.Answer
import com.example.test_platform.domain.test.Question
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.components.avatar.uuidIndex
import com.example.test_platform.presentation.screens.quiz.solve.LocalInteractionAllowed
import com.example.test_platform.presentation.screens.quiz.solve.models.SolvingQuestion
import com.example.test_platform.presentation.theme.Gradients
import com.example.test_platform.presentation.theme.QuizTheme
import java.util.UUID

val variantShape = RoundedCornerShape(8.dp)
private val questionShape = RoundedCornerShape(16.dp)

private class QuestionSolveSubScreen(question: Question) : SubScreen<SolvingQuestion> {
    override val key: ScreenKey = question.id
    override var state: SolvingQuestion by mutableStateOf(SolvingQuestion(question))
        private set

    @Composable
    override fun Content() {
        val answers = state.underlying.variants
        val question = state.underlying
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item("question") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 250.dp)
                        .background(Gradients[question.id.uuidIndex()], questionShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = question.text,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            itemsIndexed(items = answers, key = { _, v -> v.id }) { i, variant ->
                val isSelected = variant.id in state.markedAnswers
                val background by animateColorAsState(
                    targetValue = if (isSelected) QuizTheme.blue2.copy(alpha = 0.1f) else Color.Transparent
                )
                val border by animateColorAsState(
                    targetValue = if (isSelected) QuizTheme.blue2 else QuizTheme.gray
                )

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
                        text = "${i + 1})",
                        fontSize = 16.sp,
                        color = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        modifier = Modifier.weight(1f),
                        text = variant.text,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    when (question.type) {
                        Question.Type.Multiple -> Checkbox(
                            modifier = Modifier.size(32.dp),
                            checked = isSelected,
                            enabled = LocalInteractionAllowed.current,
                            onCheckedChange = { checked ->
                                val updated = state.markedAnswers.let {
                                    if (checked) {
                                        it + variant.id
                                    } else {
                                        it - variant.id
                                    }
                                }
                                state = state.copy(markedAnswers = updated)
                            },
                            colors = CheckboxDefaults.colors().copy(
                                checkedBoxColor = QuizTheme.blue2,
                                checkedBorderColor = QuizTheme.blue2
                            )
                        )
                        Question.Type.Single -> RadioButton(
                            modifier = Modifier.size(32.dp),
                            selected = isSelected,
                            enabled = LocalInteractionAllowed.current,
                            onClick = {
                                state = state.copy(markedAnswers = setOf(variant.id))
                            },
                            colors = RadioButtonDefaults.colors().copy(
                                selectedColor = QuizTheme.blue2
                            )
                        )
                    }
                }
            }
        }
    }
}

@Suppress("FunctionName")
fun QuestionSolveScreen(question: Question): SubScreen<SolvingQuestion> =
    QuestionSolveSubScreen(question)

@Composable
@Preview
private fun SolvePreview() {
    val question = Question(
        id = UUID.randomUUID().toString(),
        text = LoremIpsum(20).values.joinToString(separator = ""),
        variants = listOf(
            Answer(UUID.randomUUID().toString(), LoremIpsum(2).values.joinToString(separator = "")),
            Answer(UUID.randomUUID().toString(), LoremIpsum(2).values.joinToString(separator = "")),
            Answer(UUID.randomUUID().toString(), LoremIpsum(2).values.joinToString(separator = "")),
            Answer(UUID.randomUUID().toString(), LoremIpsum(2).values.joinToString(separator = "")),
            Answer(UUID.randomUUID().toString(), LoremIpsum(2).values.joinToString(separator = ""))
        ),
        type = Question.Type.Multiple
    )
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().systemBarsPadding().padding(horizontal = 24.dp)
        ) {
            val screen = remember { QuestionSolveScreen(question) }
            screen.Content()
        }
    }
}