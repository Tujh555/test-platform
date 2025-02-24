package com.example.test_platform.domain.auth

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>

    suspend fun signUp(email: String, password: String): Result<Unit>

    suspend fun logout(): Result<Unit>
}