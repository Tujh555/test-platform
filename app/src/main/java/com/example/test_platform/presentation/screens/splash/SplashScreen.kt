package com.example.test_platform.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test_platform.R
import com.example.test_platform.presentation.base.StateComponent
import com.example.test_platform.presentation.screens.main.MainScreen
import com.example.test_platform.presentation.screens.signin.SignInScreen
import com.example.test_platform.presentation.theme.QuizTheme

class SplashScreen : StateComponent<Nothing, Boolean?> {
    @Composable
    override fun Content(state: Boolean?, onAction: (Nothing) -> Unit) {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizTheme.cream),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.img_logo),
                contentDescription = null
            )
            Spacer(Modifier.size(20.dp))
            Text(text = "Quizzo", fontSize = 57.sp, color = Color.Black)
        }

        LaunchedEffect(state) {
            when (state) {
                true -> navigator.replace(MainScreen())
                false -> navigator.replace(SignInScreen())
                null -> Unit
            }
        }
    }

    @Composable
    override fun model(): SplashModel = getScreenModel()
}