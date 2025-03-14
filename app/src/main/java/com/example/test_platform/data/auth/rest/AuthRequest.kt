package com.example.test_platform.data.auth.rest

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)