package com.example.test_platform.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.test_platform.presentation.error.ErrorHandler
import com.example.test_platform.presentation.screens.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

val LocalRootNavigator = staticCompositionLocalOf<Navigator> {
    error("RootNavigator is not provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var handler: Handler

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            handler.scope = rememberCoroutineScope()
            MaterialTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = handler.hostState,
                            modifier = Modifier.imePadding()
                        )
                    }
                ) { _ ->
                    Navigator(SplashScreen()) {
                        CompositionLocalProvider(LocalRootNavigator provides it) {
                            SlideTransition(it)
                        }
                    }
                }
            }
        }
    }

    @Stable
    @Singleton
    class Handler @Inject constructor() : ErrorHandler {
        var scope: CoroutineScope? = null
        val hostState = SnackbarHostState()

        override fun handle(message: String) {
            scope?.launch {
                hostState.showSnackbar(message = message, withDismissAction = true)
            }
        }
    }
}