package com.example.test_platform.presentation.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.example.test_platform.presentation.base.StateComponent

class SignUpScreen : StateComponent<SignUpScreen.Action, SignUpScreen.State> {
    @Immutable
    data class State(
        val email: String = "",
        val password: String = "",
        val passwordConfirm: String = "",
        val isLoading: Boolean = false,
        val isPasswordMatches: Boolean = true
    )

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Email(val value: String) : Action
        @JvmInline
        value class Password(val value: String) : Action
        @JvmInline
        value class PasswordConfirm(val value: String) : Action
        @JvmInline
        value class Register(val navigator: Navigator) : Action
    }

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) {
        SignUpScreenContent(state, onAction)
    }

    @Composable
    override fun model(): SignUpModel = getScreenModel()
}