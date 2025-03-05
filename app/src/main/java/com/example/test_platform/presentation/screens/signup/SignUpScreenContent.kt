package com.example.test_platform.presentation.screens.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test_platform.presentation.components.PasswordTextField
import com.example.test_platform.presentation.components.Scaffold
import com.example.test_platform.presentation.components.defaultScaffoldModifier
import com.example.test_platform.presentation.theme.QuizTheme

private val shape = RoundedCornerShape(8.dp)
@Composable
fun onCreamTextFieldColors() = OutlinedTextFieldDefaults.colors().copy(
    focusedIndicatorColor = QuizTheme.border,
    unfocusedIndicatorColor = QuizTheme.border.copy(alpha = 0.75f),
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black.copy(alpha = 0.75f),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    errorContainerColor = Color.White,
)


@Composable
fun SignUpScreenContent(state: SignUpScreen.State, onAction: (SignUpScreen.Action) -> Unit) {
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        modifier = defaultScaffoldModifier
            .padding(bottom = 30.dp)
            .verticalScroll(rememberScrollState()),
        title = "Register to get started",
        isBackVisible = true,
        back = { navigator.pop() }
    ) {
        val textFieldColors = onCreamTextFieldColors()
        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(SignUpScreen.Action.Email(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Email") },
            colors = textFieldColors,
            shape = shape,
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            text = state.password,
            colors = textFieldColors,
            shape = shape,
            error = state.isPasswordMatches.not(),
            onValueChange = { onAction(SignUpScreen.Action.Password(it)) },
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            text = state.passwordConfirm,
            colors = textFieldColors,
            shape = shape,
            error = state.isPasswordMatches.not(),
            placeholder = "Confirm password",
            onValueChange = { onAction(SignUpScreen.Action.PasswordConfirm(it)) },
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            onClick = { onAction(SignUpScreen.Action.Register(navigator)) },
            content = {
                AnimatedContent(
                    targetState = state.isLoading,
                    transitionSpec = {
                        fadeIn(tween(400)) togetherWith fadeOut(tween(200))
                    }
                ) { loading ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.White,
                            )
                        } else {
                            Text(text = "Register", color = Color.White)
                        }
                    }
                }
            },
            shape = shape,
            enabled = true,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizTheme.blue2,
                contentColor = Color.White,
            ),
        )
    }
}