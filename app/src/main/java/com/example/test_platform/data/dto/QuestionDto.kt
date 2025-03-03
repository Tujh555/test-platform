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
    @SerializedName("answers")
    val answers: List<AnswerDto>?
)

fun QuestionDto.toDomain() = Question(
    id = id,
    text = text.orEmpty(),
    variants = variants.orEmpty().map(AnswerDto::toDomain),
    answers = answers.orEmpty().map(AnswerDto::toDomain)
)

fun Question.toDto() = QuestionDto(
    id = id,
    text = text,
    variants = variants.map(Answer::toDto),
    answers = answers.map(Answer::toDto)
)