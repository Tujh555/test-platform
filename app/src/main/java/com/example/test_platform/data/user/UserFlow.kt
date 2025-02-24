package com.example.test_platform.data.user

import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.data.dto.toDomain
import com.example.test_platform.data.store.Store
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserFlow @Inject constructor(private val store: Store<UserDto>) : ReactiveUser, Flow<User?> by store.user()

private fun Store<UserDto>.user() = data.map { it?.toDomain() }