package com.example.test_platform.presentation.screens.main.profile

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.domain.user.ProfileRepository
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.domain.user.User
import com.example.test_platform.presentation.base.EventEmitter
import com.example.test_platform.presentation.base.Model
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.io
import com.example.test_platform.presentation.error.ErrorHandler
import com.example.test_platform.withMinDelay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
class ProfileTabModel @Inject constructor(
    private val reactiveUser: ReactiveUser,
    private val repository: ProfileRepository,
    private val errorHandler: ErrorHandler,
    private val authRepository: AuthRepository,
) : Model<ProfileTab.Action, ProfileTab.State, ProfileTab.Event>,
    StateHolder<ProfileTab.State> by StateHolder(
        initialState = ProfileTab.State(
            id = reactiveUser.value?.id.orEmpty(),
            avatar = reactiveUser.value?.avatar,
            name = reactiveUser.value?.name.orEmpty()
        )
    ),
    EventEmitter<ProfileTab.Event> by EventEmitter() {

    private val user = MutableStateFlow<User?>(null)

    init {
        observeUser()
    }

    override fun onAction(action: ProfileTab.Action) {
        when (action) {
            is ProfileTab.Action.Name -> update { state ->
                state.copy(
                    name = action.value,
                    finishVisible = action.value.isNotEmpty() && action.value != user.value?.name
                )
            }

            ProfileTab.Action.Save -> save()

            is ProfileTab.Action.UploadAvatar -> uploadAvatar(action.uri)
            ProfileTab.Action.Logout -> logout()
        }
    }

    private fun logout() {
        screenModelScope.io {
            authRepository
                .logout()
                .onFailure { errorHandler.handle("Failed to logout") }
                .onSuccess { emit(ProfileTab.Event.Logout()) }
        }
    }

    private fun uploadAvatar(uri: Uri) {
        screenModelScope.io {
            withRefresh {
                withMinDelay {
                    repository
                        .uploadAvatar(uri)
                        .onFailure {
                            val name = uri.pathSegments?.lastOrNull().orEmpty()
                            errorHandler.handle("Failed to upload $name")
                        }
                }
            }
        }
    }

    private fun save() {
        screenModelScope.io {
            withRefresh {
                withMinDelay {
                    val name = state.value.name
                    repository
                        .updateName(name)
                        .onFailure { errorHandler.handle("Failed to update name") }
                }
            }
            update { it.copy(finishVisible = user.value?.name != state.value.name) }
        }
    }

    private inline fun withRefresh(block: () -> Unit) {
        state.value.ptr.run {
            startRefresh()
            block()
            endRefresh()
        }
    }

    private fun observeUser() {
        screenModelScope.launch {
            reactiveUser
                .filterNotNull()
                .onEach { user ->
                    update { state ->
                        state.copy(
                            id = user.id,
                            name = user.name,
                            avatar = user.avatar
                        )
                    }
                }
                .collect(user)
        }
    }
}