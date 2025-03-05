package com.example.test_platform.data.user

import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.data.dto.toDomain
import com.example.test_platform.data.store.Store
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.domain.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UserFlow @Inject constructor(
    private val store: Store<UserDto>,
    scope: CoroutineScope
) : ReactiveUser, StateFlow<User?> by store.user(scope)

private fun Store<UserDto>.user(scope: CoroutineScope) = data
    .map { it?.toDomain() }
    .stateIn(scope, SharingStarted.Eagerly, null)