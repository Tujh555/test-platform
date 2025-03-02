package com.example.test_platform.presentation.screens.signin

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import com.example.test_platform.presentation.screens.signup.SignUpScreen
import com.example.test_platform.presentation.screens.signup.onCreamTextFieldColors
import com.example.test_platform.presentation.theme.QuizTheme

private val shape = RoundedCornerShape(8.dp)

@Composable
fun SignInScreenContent(state: SignInScreen.State, onAction: (SignInScreen.Action) -> Unit) {
    Scaffold(
        modifier = defaultScaffoldModifier
            .padding(bottom = 20.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        title = "Welcome back!",
        isBackVisible = false,
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val textFieldColors = onCreamTextFieldColors()

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(SignInScreen.Action.Email(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Email") },
            colors = textFieldColors,
            shape = shape
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            text = state.password,
            colors = textFieldColors,
            shape = shape,
            onValueChange = { onAction(SignInScreen.Action.Password(it)) },
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            onClick = { onAction(SignInScreen.Action.Login(navigator)) },
            content = {
                AnimatedContent(
                    targetState = state.loginInProgress,
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
                            Text(text = "Login", color = Color.White)
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

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account? ", color = Color.Black)
            Text(
                modifier = Modifier.clickable { navigator.push(SignUpScreen()) },
                text = "Register now",
                color = QuizTheme.darkBlue
            )
        }
    }
}