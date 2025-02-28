package com.example.test_platform.presentation.screens.main.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_platform.presentation.components.avatar.UserAvatar
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.screens.signup.onCreamTextFieldColors
import com.example.test_platform.presentation.theme.QuizTheme

@Composable
fun ProfileTabContent(state: ProfileTab.State, onAction: (ProfileTab.Action) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .imePadding()) {
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
                Box(contentAlignment = Alignment.Center) {
                    UserAvatar(
                        modifier = Modifier.size(128.dp),
                        url = state.avatar,
                        userId = state.id,
                        onClick = {
                            if (state.avatarSending.not()) {
                                val request = PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                                galleryLauncher.launch(request)
                            }
                        }
                    )

                    this@Column.AnimatedVisibility(visible = state.avatarSending) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onAction(ProfileTab.Action.Name(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = onCreamTextFieldColors(),
                    shape = RoundedCornerShape(8.dp),
                    enabled = state.nameSending.not()
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd).padding(start = 12.dp, bottom = 12.dp),
            visible = state.finishVisible
        ) {
            FloatingActionButton(
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
    }
}