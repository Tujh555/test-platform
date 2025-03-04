package com.example.test_platform.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test_platform.presentation.theme.QuizTheme

@Composable
fun ErrorState(modifier: Modifier, onRetry: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error occurred", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            onClick = onRetry,
            content = { Text(text = "Retry", color = Color.White) },
            shape = RoundedCornerShape(8.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )
    }
}

@Composable
fun EmptyState(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Not found", color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LoadState(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = QuizTheme.violet
        )
    }
}

enum class Stub {
    Loading, Error, Empty, Loaded
}

@Composable
fun StubState(
    modifier: Modifier,
    stub: Stub,
    transition: AnimatedContentTransitionScope<Stub>.() -> ContentTransform = { fadeIn() togetherWith fadeOut() },
    onRetry: () -> Unit,
    loadedContent: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = stub,
        transitionSpec = transition
    ) { targetStub ->
        when (targetStub) {
            Stub.Loading -> LoadState(modifier)
            Stub.Error -> ErrorState(modifier, onRetry)
            Stub.Empty -> EmptyState(modifier)
            Stub.Loaded -> loadedContent()
        }
    }
}