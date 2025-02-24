package com.example.test_platform.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.test_platform.presentation.theme.QuizTheme

private val defaultShape = RoundedCornerShape(12.dp)

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    shape: Shape = defaultShape,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(Color.White, shape)
            .border(0.5.dp, QuizTheme.border, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.Black
        )
    }
}