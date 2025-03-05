package com.example.test_platform.presentation.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.test_platform.presentation.base.TabComponent
import com.example.test_platform.presentation.theme.QuizTheme

private val shape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    fabSize: Dp = 68.dp,
    tabs: List<TabComponent<*, *, *>>,
    onFabClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .shadow(elevation = 32.dp, shape = shape, clip = false)
            .background(color = Color.White, shape = shape)
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            modifier = Modifier
                .size(fabSize)
                .offset {
                    val y = fabSize.roundToPx() / 2
                    IntOffset(0, -y)
                },
            shape = CircleShape,
            containerColor = QuizTheme.blue,
            contentColor = Color.White,
            onClick = onFabClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = null,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val half = tabs.size / 2
            for (i in 0 ..< half) {
                val tab = tabs[i]
                key(tab.key) {
                    BottomNavigationItem(Modifier, tab)
                }
            }

            Spacer(modifier = Modifier.size(fabSize))

            for (i in half ..< tabs.size) {
                val tab = tabs[i]
                key(tab.key) {
                    BottomNavigationItem(Modifier, tab)
                }
            }
        }
    }
}