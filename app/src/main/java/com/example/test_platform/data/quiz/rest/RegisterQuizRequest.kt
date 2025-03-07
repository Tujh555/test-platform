package com.example.test_platform.data.quiz.rest

import com.example.test_platform.data.dto.QuizDto
import com.google.gson.annotations.SerializedName

class RegisterQuizRequest(
    @SerializedName("quiz")
    val quiz: QuizDto,
    @SerializedName("right_answers")
    val rightAnswers: Map<Int, Iterable<Int>>
)