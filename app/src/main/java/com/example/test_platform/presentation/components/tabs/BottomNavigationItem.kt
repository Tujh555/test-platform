package com.example.test_platform.presentation.components.tabs

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.test_platform.presentation.base.TabComponent
import com.example.test_platform.presentation.theme.QuizTheme

private val selected = Color(0xFF49454F)
private val unselected = Color(0xFF6D6D6D)

@Composable
fun BottomNavigationItem(modifier: Modifier = Modifier, tab: TabComponent<*, *>) {
    val isSelected = LocalTabNavigator.current.current == tab
    val title = tab.title
    val iconRes = tab.icon(isSelected)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        val backgroundColor by animateColorAsState(
            targetValue = if (isSelected) QuizTheme.blue else Color.White
        )
        val textColor by animateColorAsState(
            targetValue = if (isSelected) selected else unselected
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(backgroundColor, CircleShape)
                .padding(horizontal = 20.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            val iconColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else unselected
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconColor
            )
        }

        Text(text = title, color = textColor)
    }
}