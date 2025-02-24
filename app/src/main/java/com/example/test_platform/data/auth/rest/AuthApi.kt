package com.example.test_platform.data.auth.rest

import com.example.test_platform.data.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.UUID

interface AuthApi {
    @POST("/auth/login")
    suspend fun signIn(@Body body: AuthRequest): Result<AuthResponse>

    @POST("/auth/register")
    suspend fun signUp(@Body body: AuthRequest): Result<AuthResponse>

    @POST("/auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Result<Unit>

    companion object Mock : AuthApi {
        val user = UserDto(UUID.randomUUID().toString(), null, "Test name", "ru")
        override suspend fun signIn(body: AuthRequest): Result<AuthResponse> =
            Result.success(AuthResponse(user, UUID.randomUUID().toString()))

        override suspend fun signUp(body: AuthRequest): Result<AuthResponse> =
            Result.success(AuthResponse(user, UUID.randomUUID().toString()))

        override suspend fun logout(request: LogoutRequest): Result<Unit> =
            Result.success(Unit)
    }
}