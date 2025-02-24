package com.example.test_platform.presentation.screens.splash

import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.presentation.base.BaseStateModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class SplashModel @Inject constructor(
    private val reactiveUser: ReactiveUser
) : BaseStateModel<Nothing, Boolean?>(initial = null) {
    init {
        ioScope.launch { loadUser() }
    }

    override fun onAction(action: Nothing) = Unit

    private suspend fun loadUser() {
        coroutineScope {
            val user = async {
                withTimeoutOrNull(1000) { reactiveUser.filterNotNull().first() }
            }
            delay(1000)
            _state.value = user.await() != null
        }
    }
}