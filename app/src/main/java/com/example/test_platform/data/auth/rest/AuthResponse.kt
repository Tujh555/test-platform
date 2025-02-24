package com.example.test_platform.data.auth.rest

import com.example.test_platform.data.dto.UserDto
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("token")
    val token: String
)
