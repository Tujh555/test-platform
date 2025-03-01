package com.example.test_platform.presentation.screens.main.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.domain.user.User
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
class ProfileTabModel @Inject constructor(
    private val reactiveUser: ReactiveUser,
) : StateModel<ProfileTab.Action, ProfileTab.State>,
    StateHolder<ProfileTab.State> by StateHolder(ProfileTab.State()) {

    private var lastUser: User? = null

    init {
        observeUser()
    }

    override fun onAction(action: ProfileTab.Action) {
        when (action) {
            is ProfileTab.Action.Name -> update { state ->
                state.copy(
                    name = action.value,
                    finishVisible = action.value.isNotEmpty() && action.value != lastUser?.name
                )
            }

            ProfileTab.Action.Save -> {
                screenModelScope.launch {
                    state.value.pullToRefreshState.startRefresh()
                    delay(500)
                    state.value.pullToRefreshState.endRefresh()
                    update { it.copy(finishVisible = false) }
                }
            }

            is ProfileTab.Action.UploadAvatar -> {
                screenModelScope.launch {
                    state.value.pullToRefreshState.startRefresh()
                    delay(1000)
                    state.value.pullToRefreshState.endRefresh()
                }
            }
        }
    }

    private fun observeUser() {
        reactiveUser
            .filterNotNull()
            .onEach { user ->
                lastUser = user
                update { state ->
                    state.copy(
                        id = user.id,
                        name = user.name,
                        avatar = user.avatar
                    )
                }
            }
            .launchIn(screenModelScope)
    }
}