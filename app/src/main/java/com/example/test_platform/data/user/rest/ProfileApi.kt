package com.example.test_platform.data.user.rest

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {
    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): Result<AvatarUpdateResponse>

    @PATCH("user/name")
    suspend fun updateName(@Body name: String): Result<Unit>
}