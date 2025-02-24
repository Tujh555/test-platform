package com.example.test_platform.data.auth

import com.example.test_platform.data.auth.rest.AuthApi
import com.example.test_platform.data.auth.rest.AuthRequest
import com.example.test_platform.data.auth.rest.AuthResponse
import com.example.test_platform.data.auth.rest.LogoutRequest
import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.data.get
import com.example.test_platform.data.map
import com.example.test_platform.data.store.Store
import com.example.test_platform.domain.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userStore: Store<UserDto>,
    private val tokenStore: Store<String>,
    private val api: AuthApi
) : AuthRepository {
    override suspend fun signIn(email: String, password: String) =
        authRequest(email, password, api::signIn)

    override suspend fun signUp(email: String, password: String) =
        authRequest(email, password, api::signUp)

    override suspend fun logout(): Result<Unit> {
        val user = userStore.get() ?: return Result.failure(NullPointerException())

        return api
            .logout(LogoutRequest(user.id))
            .onSuccess {
                tokenStore.clear()
                userStore.clear()
            }
    }

    private suspend fun authRequest(
        email: String,
        password: String,
        factory: suspend (AuthRequest) -> Result<AuthResponse>
    ): Result<Unit> {
        return factory(AuthRequest(email, password))
            .onSuccess { response ->
                tokenStore.update(response.token)
                userStore.update(response.user)
            }
            .map()
    }
}