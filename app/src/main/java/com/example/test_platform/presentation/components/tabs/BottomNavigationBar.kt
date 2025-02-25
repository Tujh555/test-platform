package com.example.test_platform.presentation.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    fabSize: Dp = 68.dp,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithCache {
                val cornerRadius = CornerRadius(20.dp.toPx())
                val radius = fabSize.toPx() / 2
                val circleOffset = Offset(x = size.width / 2, 0f)

                val path = Path().apply {
                    val starX = size.width / 2 - radius - 50f
                    val endX = starX * 2
                    val midX = starX
                }

                onDrawWithContent {
                    drawRoundRect(color = Color.Black, cornerRadius = cornerRadius)
                    drawContent()
                    drawCircle(
                        color = Color.Red,
                        radius = radius,
                        center = circleOffset,
                        blendMode = BlendMode.Clear
                    )
                }
            }
    ) {
        content()
    }
}

@Composable
@Preview
private fun BarPreview() {
    Box(
        modifier = Modifier.background(Color.White).fillMaxWidth().height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        BottomNavigationBar(modifier = Modifier.fillMaxWidth().height(100.dp)) {  }
    }
}