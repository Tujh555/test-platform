package com.example.test_platform.presentation.screens.signin

import android.content.Context
import android.widget.Toast
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

class SignInModel @Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) : BaseStateModel<SignInScreen.Action, SignInScreen.State>(SignInScreen.State()) {
    override fun onAction(action: SignInScreen.Action) {
        when (action) {
            is SignInScreen.Action.Email -> _state.update {
                it.copy(email = action.value)
            }
            is SignInScreen.Action.Password -> _state.update {
                it.copy(password = action.value)
            }
            is SignInScreen.Action.Login -> ioScope.launch {
                _state.update {
                    it.copy(loginInProgress = true)
                }
                val result = withMinDelay {
                    val (email, password) = _state.value.run { email to password }
                    repository.signIn(email, password)
                }

                result
                    .onSuccess { action.navigator.replaceAll(MainScreen()) }
                    .onFailure {
                        _state.update { it.copy(loginInProgress = false) }

                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}