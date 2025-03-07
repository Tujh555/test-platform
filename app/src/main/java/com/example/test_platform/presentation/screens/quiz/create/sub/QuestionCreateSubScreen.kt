package com.example.test_platform.presentation.screens.quiz.create.sub

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastMap
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.example.test_platform.R
import com.example.test_platform.domain.test.Question
import com.example.test_platform.presentation.base.sub.SubScreen
import com.example.test_platform.presentation.screens.quiz.create.models.RawAnswer
import com.example.test_platform.presentation.screens.quiz.create.models.RawQuestion
import com.example.test_platform.presentation.theme.QuizTheme

val tfShape = RoundedCornerShape(4.dp)

private class QuestionCreateSubScreen : SubScreen<RawQuestion> {
    override val key: ScreenKey = uniqueScreenKey
    override var state: RawQuestion by mutableStateOf(RawQuestion())
        private set

    @Composable
    override fun Content() {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item("title") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.text,
                        onValueChange = { state = state.copy(text = it) },
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(
                                text = "Quiz question",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        },
                        shape = tfShape,
                    )

                    Checkbox(
                        modifier = Modifier.size(32.dp),
                        checked = state.type.multiple(),
                        onCheckedChange = {
                            state = state.copy(
                                type = Question.Type.Multiple,
                                answers = state.answers.fastMap { it.copy(marked = false) }
                            )
                        },
                        colors = CheckboxDefaults.colors().copy(
                            checkedBoxColor = QuizTheme.blue2,
                            checkedBorderColor = QuizTheme.blue2
                        )
                    )

                    RadioButton(
                        modifier = Modifier.size(32.dp),
                        selected = state.type.single(),
                        onClick = {
                            state = state.copy(
                                type = Question.Type.Single,
                                answers = state.answers.fastMap { it.copy(marked = false) }
                            )
                        },
                        colors = RadioButtonDefaults.colors().copy(selectedColor = QuizTheme.blue2)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item("subtitle") {
                Text(text = "Quiz question", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(items = state.answers, key = { _, answer -> answer.id }) { i, answer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = answer.text,
                        onValueChange = { text ->
                            val updated = state.answers.update(answer.id) { it.copy(text = text) }
                            state = state.copy(answers = updated)
                        },
                        label = {
                            Text(
                                text = "Question ${i + 1}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        },
                        shape = tfShape,
                    )

                    when (state.type) {
                        Question.Type.Multiple -> {
                            Checkbox(
                                modifier = Modifier.size(32.dp),
                                checked = answer.marked,
                                onCheckedChange = { checked ->
                                    val updated = state.answers.update(answer.id) {
                                        it.copy(marked = checked)
                                    }
                                    state = state.copy(answers = updated)
                                },
                                colors = CheckboxDefaults.colors().copy(
                                    checkedBoxColor = QuizTheme.blue2,
                                    checkedBorderColor = QuizTheme.blue2
                                )
                            )
                        }

                        Question.Type.Single -> {
                            RadioButton(
                                modifier = Modifier.size(32.dp),
                                selected = answer.marked,
                                onClick = {
                                    val updated = state.answers.fastMap { old ->
                                        if (old.id == answer.id) {
                                            old.copy(marked = true)
                                        } else {
                                            old.copy(marked = false)
                                        }
                                    }
                                    state = state.copy(answers = updated)
                                },
                                colors = RadioButtonDefaults.colors().copy(
                                    selectedColor = QuizTheme.blue2
                                )
                            )
                        }
                    }

                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable {
                                val filtered = state.answers.filter { it.id != answer.id }
                                state = state.copy(answers = filtered)
                            },
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        tint = QuizTheme.red
                    )
                }
            }

            item("add") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(tfShape)
                        .clickable {
                            state = state.copy(answers = state.answers + RawAnswer())
                        }
                        .animateItem(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = QuizTheme.blue2
                    )
                    Text(text = "Add question", color = Color.Black)
                }
            }
        }
    }

    private fun Question.Type.single() = this == Question.Type.Single

    private fun Question.Type.multiple() = this == Question.Type.Multiple

    private inline fun List<RawAnswer>.update(id: String, block: (RawAnswer) -> RawAnswer) =
        fastMap { answer ->
            if (answer.id == id) {
                block(answer)
            } else {
                answer
            }
        }
}

@Suppress("FunctionName")
fun QuestionCreateScreen(i: Int): SubScreen<RawQuestion> = QuestionCreateSubScreen()