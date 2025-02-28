package com.example.test_platform.domain.user

import android.net.Uri

interface ProfileRepository {
    suspend fun updateName(name: String): Result<Unit>

    suspend fun uploadAvatar(uri: Uri): Result<Unit>
}