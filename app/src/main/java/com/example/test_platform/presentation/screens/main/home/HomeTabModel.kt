package com.example.test_platform.presentation.screens.main.home

import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeTabModel @Inject constructor(
    private val reactiveUser: ReactiveUser
) : StateModel<HomeTab.Action, HomeTab.State>,
    StateHolder<HomeTab.State> by StateHolder(HomeTab.State(reactiveUser.value!!)) {

    init {
        reactiveUser
            .onEach { user ->
                if (user != null) {
                    update { it.copy(user = user) }
                }
            }
            .launchIn(screenModelScope)
    }

    override fun onAction(action: HomeTab.Action) {

    }
}