package com.example.test_platform.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.presentation.theme.QuizTheme

val defaultScaffoldModifier by lazy {
    Modifier
        .fillMaxSize()
        .background(QuizTheme.cream)
        .systemBarsPadding()
        .screenPadding()
}

@Composable
fun Scaffold(
    modifier: Modifier = defaultScaffoldModifier,
    topPadding: Dp = 12.dp,
    appBar: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Spacer(Modifier.height(topPadding))
        appBar()
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
fun Scaffold(
    modifier: Modifier = defaultScaffoldModifier,
    title: String,
    topPadding: Dp = 12.dp,
    isBackVisible: Boolean = true,
    back: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topPadding = topPadding,
        appBar = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (isBackVisible) {
                    BackButton(onClick = back)
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = content
    )
}