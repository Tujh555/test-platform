package com.example.test_platform.presentation.theme

import androidx.compose.ui.graphics.Color

object QuizTheme {
    val cream = Color(0xFFF5EDE2)
    val lightBlue2 = Color(0xFFC4D0FB)
    val lightBlue = Color(0xFF7B89FF)
    val blue = Color(0xFF2120FF)
    val blue2 = Color(0xFF4D61DE)
    val darkBlue = Color(0xFF0D0BCC)
    val violet = Color(0xFF6A5AE0)
    val darkViolet = Color(0xFF8950FC)
    val pink = Color(0xFFFFC2CD)
    val pink2 = Color(0xFFF2B1AF)
    val darkPink = Color(0xFFF6B191)
    val red = Color(0xFFC9353F)
    val darkGray = Color(0xFF5D5D5D)
    val lightGray = Color(0xFFE1E1E2)
    val yellow = Color(0xFFFFD54B)
    val border = Color(0xFFE8ECF4)
    val supportedText = Color(0xFFE7E7E7)
    val gray = Color(0xFFD1D1D1)

    val allColors = listOf(
        lightBlue2,
        lightBlue,
        blue,
        blue2,
        darkBlue,
        violet,
        darkViolet,
        pink,
        pink2,
        darkPink,
        red,
        darkGray,
        yellow
    )
}

fun QuizTheme.color(index: Long) = allColors[(index % allColors.size).toInt()]