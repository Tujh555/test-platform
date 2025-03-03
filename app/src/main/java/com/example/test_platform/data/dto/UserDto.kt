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
) {
    companion object {
        val unknown = UserDto(
            id = "",
            avatar = null,
            name = "unknown"
        )
    }
}

fun UserDto.toDomain() = User(id, avatar, name)

fun User.toDto() = UserDto(id, avatar, name)