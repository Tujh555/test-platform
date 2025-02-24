package com.example.test_platform.presentation.screens.signin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.test_platform.R
import com.example.test_platform.presentation.components.screenPadding
import com.example.test_platform.presentation.theme.QuizTheme

private val shape = RoundedCornerShape(8.dp)
private val passwordVisualTransformation = VisualTransformation { original ->
    val text = AnnotatedString("●".repeat(original.length))
    TransformedText(text, OffsetMapping.Identity)
}

@Composable
fun SignInScreenContent(state: SignInScreen.State, onAction: (SignInScreen.Action) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuizTheme.cream)
            .systemBarsPadding()
            .screenPadding()
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome back!", fontSize = 32.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(32.dp))
        val textFieldColors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = QuizTheme.border,
            unfocusedIndicatorColor = QuizTheme.border.copy(alpha = 0.75f),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black.copy(alpha = 0.75f),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(SignInScreen.Action.Email(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Email") },
            colors = textFieldColors,
            shape = shape
        )

        var inputVisible by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(SignInScreen.Action.Password(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Password") },
            visualTransformation = if (inputVisible) {
                VisualTransformation.None
            } else {
                passwordVisualTransformation
            },
            colors = textFieldColors,
            trailingIcon = {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_eye))
                val progress = remember { Animatable(0.5f) }

                LaunchedEffect(inputVisible) {
                    progress.animateTo(
                        targetValue = if (inputVisible) 0f else 0.5f,
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = LinearEasing
                        )
                    )
                }

                LottieAnimation(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { inputVisible = inputVisible.not() }
                        ),
                    composition = composition,
                    progress = { progress.value }
                )
            },
            shape = shape
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(SignInScreen.Action.Login) },
            content = { Text(text = "Login", color = Color.White) },
            shape = shape,
            enabled = true,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account? ", color = Color.Black)
            Text(
                modifier = Modifier.clickable { onAction(SignInScreen.Action.Login) },
                text = "Register now",
                color = QuizTheme.darkBlue
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        var state by remember { mutableStateOf(SignInScreen.State()) }

        SignInScreenContent(state) { action ->
            when (action) {
                is SignInScreen.Action.Email -> state = state.copy(
                    email = action.value
                )
                is SignInScreen.Action.Password -> state = state.copy(
                    password = action.value
                )
                SignInScreen.Action.Login,
                SignInScreen.Action.SignUp -> Unit
            }
        }
    }
}