package com.example.test_platform.presentation.screens.signup

import android.content.Context
import android.widget.Toast
import cafe.adriel.voyager.navigator.Navigator
import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.presentation.base.BaseStateModel
import com.example.test_platform.presentation.screens.main.MainScreen
import com.example.test_platform.withMinDelay
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpModel @Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) : BaseStateModel<SignUpScreen.Action, SignUpScreen.State>(
    initial = SignUpScreen.State()
) {
    override fun onAction(action: SignUpScreen.Action) {
        when (action) {
            is SignUpScreen.Action.Email -> _state.update {
                it.copy(email = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.Password -> _state.update {
                it.copy(password = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.PasswordConfirm -> _state.update {
                it.copy(passwordConfirm = action.value, isPasswordMatches = true)
            }
            is SignUpScreen.Action.Register -> register(action.navigator)
        }
    }

    private fun register(navigator: Navigator) {
        val (password, confirmation) = state.value.run { password to passwordConfirm }

        if (password != confirmation) {
            _state.update { it.copy(isPasswordMatches = false) }
            Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            return
        }

        val email = state.value.email

        ioScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = withMinDelay {
                repository.signUp(email, password)
            }

            result
                .onSuccess { navigator.replaceAll(MainScreen()) }
                .onFailure {
                    _state.update { it.copy(isLoading = false) }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}