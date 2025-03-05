package com.example.test_platform.presentation.screens.splash

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.base.io
import com.example.test_platform.withMinDelay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class SplashModel @Inject constructor(
    private val reactiveUser: ReactiveUser
) : StateModel<Nothing, Boolean?>,
    StateHolder<Boolean?> by StateHolder(null) {

    init {
        screenModelScope.io {
            loadUser()
        }
    }

    override fun onAction(action: Nothing) = Unit

    private suspend fun loadUser() {
        val user = withMinDelay(1000) {
            withTimeoutOrNull(1000) {
                reactiveUser.filterNotNull().first()
            }
        }
        Log.d("--tag", "user = $user")
        update { user != null }
    }
}