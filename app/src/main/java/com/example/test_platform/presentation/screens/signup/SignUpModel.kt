package com.example.test_platform.presentation.screens.signup

import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.base.io
import com.example.test_platform.presentation.error.ErrorHandler
import com.example.test_platform.presentation.screens.main.MainScreen
import com.example.test_platform.withMinDelay
import javax.inject.Inject

class SignUpModel @Inject constructor(
    private val repository: AuthRepository,
    private val errorHandler: ErrorHandler
) : StateModel<SignUpScreen.Action, SignUpScreen.State>,
    StateHolder<SignUpScreen.State> by StateHolder(SignUpScreen.State()) {

    override fun onAction(action: SignUpScreen.Action) {
        when (action) {
            is SignUpScreen.Action.Email -> update {
                it.copy(email = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.Password -> update {
                it.copy(password = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.PasswordConfirm -> update {
                it.copy(passwordConfirm = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.Register -> register(action.navigator)
        }
    }

    private fun register(navigator: Navigator) {
        val (password, confirmation) = state.value.run { password to passwordConfirm }

        if (password != confirmation) {
            update { it.copy(isPasswordMatches = false) }
            errorHandler.handle("Password doesn't match")
            return
        }

        val email = state.value.email

        screenModelScope.io {
            update { it.copy(isLoading = true) }
            val result = withMinDelay {
                repository.signUp(email, password)
            }

            result
                .onSuccess { navigator.replaceAll(MainScreen()) }
                .onFailure {
                    update { it.copy(isLoading = false) }
                    errorHandler.handle("Error")
                }
        }
    }
}