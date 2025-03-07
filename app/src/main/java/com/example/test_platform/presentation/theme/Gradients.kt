package com.example.test_platform.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Gradients {
    private val colors = listOf(
        listOf(Color(0xFF6D53DC), Color(0xFFDB8282)),
        listOf(Color(0xFF539566), Color(0xFF72A5C2)),
        listOf(Color(0xFFE54D24), Color(0xFFDDB249)),
        listOf(Color(0xFF8D60BE), Color(0xFFF2D49E)),
        listOf(Color(0xFFA446A4), Color(0xFF83A5BE)),
        listOf(Color(0xFF6D53DC), Color(0xFFDC5356)),
        listOf(Color(0xFF04616E), Color(0xFFB7D7C9)),
        listOf(Color(0xFF181D72), Color(0xFFB491C5)),
        listOf(Color(0xFFD9B967), Color(0xFF8EC3A7)),
        listOf(Color(0xFFF08686), Color(0xFF9CC495)),
        listOf(Color(0xFF292424), Color(0xFFAA8DCB)),
        listOf(Color(0xFF413D3D), Color(0xFF867878)),
        listOf(Color(0xFF436553), Color(0xFFA0B4A9)),
        listOf(Color(0xFF40BFA8), Color(0xFFFEECAD))
    )
    private val gradients = colors.map { Brush.linearGradient(it) }

    operator fun get(i: Long) = gradients[(i % gradients.size).toInt()]
}