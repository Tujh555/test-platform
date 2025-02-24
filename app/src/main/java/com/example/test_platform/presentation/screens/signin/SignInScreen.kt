package com.example.test_platform.presentation.screens.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.presentation.base.StateComponent

class SignInScreen : StateComponent<SignInScreen.Action, SignInScreen.State> {
    @Immutable
    data class State(
        val email: String = "",
        val password: String = ""
    )

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Email(val value: String) : Action
        @JvmInline
        value class Password(val value: String) : Action
        data object Login : Action
        data object SignUp : Action
    }

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) {
        SignInScreenContent(state, onAction)
    }

    @Composable
    override fun model(): SignInModel = getScreenModel()
}