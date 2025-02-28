package com.example.test_platform.presentation.screens.signin

import android.content.Context
import android.widget.Toast
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.base.io
import com.example.test_platform.presentation.screens.main.MainScreen
import com.example.test_platform.withMinDelay
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInModel @Inject constructor(
    private val repository: AuthRepository,
    @ApplicationContext private val context: Context
) : StateModel<SignInScreen.Action, SignInScreen.State>,
    StateHolder<SignInScreen.State> by StateHolder(SignInScreen.State()) {

    override fun onAction(action: SignInScreen.Action) {
        when (action) {
            is SignInScreen.Action.Email -> update {
                it.copy(email = action.value)
            }
            is SignInScreen.Action.Password -> update {
                it.copy(password = action.value)
            }
            is SignInScreen.Action.Login -> screenModelScope.io {
                update { it.copy(loginInProgress = true) }

                val result = withMinDelay {
                    val (email, password) = state.value.run { email to password }
                    repository.signIn(email, password)
                }

                result
                    .onSuccess { action.navigator.replaceAll(MainScreen()) }
                    .onFailure {
                        update { it.copy(loginInProgress = false) }

                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}