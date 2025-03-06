package com.example.test_platform.data.quiz.rest

import com.google.gson.annotations.SerializedName

class SolveQuizRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("answers")
    val answers: Map<String, List<String>>
)