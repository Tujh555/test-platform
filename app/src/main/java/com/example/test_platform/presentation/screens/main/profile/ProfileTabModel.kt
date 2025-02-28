package com.example.test_platform.presentation.screens.main.profile

import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.user.ProfileRepository
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.domain.user.User
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.base.io
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                    update { it.copy(nameSending = true) }
                    delay(500)
                    update { it.copy(nameSending = false) }
                }
            }

            is ProfileTab.Action.UploadAvatar -> {
                screenModelScope.launch {
                    update { it.copy(avatarSending = true) }
                    delay(1000)
                    update { it.copy(avatarSending = false) }
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