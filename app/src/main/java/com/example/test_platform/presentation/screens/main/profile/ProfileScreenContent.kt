package com.example.test_platform.presentation.screens.main.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.presentation.components.avatar.UserAvatar
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.signup.onCreamTextFieldColors
import com.example.test_platform.presentation.theme.QuizTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTabContent(state: ProfileTab.State, onAction: (ProfileTab.Action) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizTheme.cream)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.screenPadding(),
                text = "Profile",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .screenPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                val galleryLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia()
                ) { uri ->
                    if (uri != null) {
                        onAction(ProfileTab.Action.UploadAvatar(uri))
                    }
                }
                UserAvatar(
                    modifier = Modifier.size(128.dp),
                    url = state.avatar,
                    userId = state.id,
                )
                Spacer(modifier = Modifier.height(12.dp))

                val isRefreshing = state.pullToRefreshState.isRefreshing
                Text(
                    modifier = Modifier.clip(CircleShape).clickable {
                        if (isRefreshing.not()) {
                            val request = PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                            galleryLauncher.launch(request)
                        }
                    },
                    text = "Choose photo",
                    color = QuizTheme.blue
                )

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onAction(ProfileTab.Action.Name(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = onCreamTextFieldColors(),
                    shape = RoundedCornerShape(8.dp),
                    enabled = isRefreshing.not()
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = state.finishVisible
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(end = 12.dp, bottom = 12.dp),
                onClick = { onAction(ProfileTab.Action.Save) },
                containerColor = QuizTheme.lightBlue,
                contentColor = Color.White
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.Done,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.TopCenter),
            visible = state.pullToRefreshState.isRefreshing,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            PullToRefreshContainer(
                state = state.pullToRefreshState,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 56.dp)
            )
        }
    }
}