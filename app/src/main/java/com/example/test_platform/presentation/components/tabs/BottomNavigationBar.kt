package com.example.test_platform.presentation.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.test_platform.R
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.Model
import com.example.test_platform.presentation.base.TabComponent
import com.example.test_platform.presentation.theme.QuizTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

private val shape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    fabSize: Dp = 68.dp,
    tabs: List<TabComponent<*, *>>,
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

private fun previewTab(index: Int) = object : TabComponent<Any, Any> {
    override val key: ScreenKey = uniqueScreenKey
    @Composable
    override fun Content(state: Any, onAction: (Any) -> Unit) = Unit

    override val title: String = "Tab $index"
    override val icons: IconPair = IconPair(
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground
    )

    @Composable
    override fun model(): Model<Any, Any, Nothing> = object : Model<Any, Any, Nothing> {
        override val state: StateFlow<Any> = MutableStateFlow(Any())
        override val event: Flow<Nothing> = emptyFlow()
        override fun onAction(action: Any) {}
    }
}

private val previewTabs = (1..4).map(::previewTab)

@Composable
@Preview
private fun BarPreview() {
    Box(
        modifier = Modifier.background(QuizTheme.lightGray).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TabNavigator(previewTabs.first()) {
            BottomNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                tabs = previewTabs
            )
        }
    }
}