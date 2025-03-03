package com.example.test_platform.data.dto

import com.example.test_platform.domain.test.Answer
import com.google.gson.annotations.SerializedName

class AnswerDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String?
)

fun AnswerDto.toDomain() = Answer(id, text.orEmpty())

fun Answer.toDto() = AnswerDto(id, text)