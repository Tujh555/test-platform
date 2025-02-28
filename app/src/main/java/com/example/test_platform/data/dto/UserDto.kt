package com.example.test_platform.data.dto

import com.example.test_platform.domain.user.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("name")
    val name: String,
)

fun UserDto.toDomain() = User(id, avatar, name)