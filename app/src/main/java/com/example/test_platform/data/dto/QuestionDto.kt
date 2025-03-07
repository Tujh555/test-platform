package com.example.test_platform.data.dto

import com.example.test_platform.domain.test.Answer
import com.example.test_platform.domain.test.Question
import com.google.gson.annotations.SerializedName

class QuestionDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String?,
    @SerializedName("variants")
    val variants: List<AnswerDto>?,
    @SerializedName("type")
    val type: String?
)

private fun String?.asQuestionType() = Question.Type.entries
    .firstOrNull { it.name.equals(other = this, ignoreCase = true) }
    ?: Question.Type.Multiple

fun QuestionDto.toDomain() = Question(
    id = id,
    text = text.orEmpty(),
    variants = variants.orEmpty().map(AnswerDto::toDomain),
    type = type.asQuestionType()
)

fun Question.toDto() = QuestionDto(
    id = id,
    text = text,
    variants = variants.map(Answer::toDto),
    type = type.name.lowercase()
)