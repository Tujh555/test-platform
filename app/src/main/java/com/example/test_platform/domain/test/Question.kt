package com.example.test_platform.domain.test

data class Question(
    val id: String,
    val text: String,
    val variants: List<Answer>,
    val type: Type
) {
    enum class Type {
        Multiple, Single
    }
}