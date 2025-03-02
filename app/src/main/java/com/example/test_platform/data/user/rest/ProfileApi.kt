package com.example.test_platform.data.user.rest

import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {
    @Multipart
    @POST("user/avatar")
    suspend fun uploadAvatar(@Part file: MultipartBody.Part): Result<String>

    @PATCH("user/name")
    suspend fun updateName(@Body name: String): Result<Unit>

    companion object Mock : ProfileApi {
        private val urls = listOf(
            "https://gravatar.com/avatar/1c8e8a6e8d1fe52b782b280909abeb38?s=400&d=robohash&r=x",
            "https://media2.dev.to/dynamic/image/width=800%2Cheight=%2Cfit=scale-down%2Cgravity=auto%2Cformat=auto/https%3A%2F%2Fwww.gravatar.com%2Favatar%2F2c7d99fe281ecd3bcd65ab915bac6dd5%3Fs%3D250",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0ETm1HSUSt367HbatCJ57a1M5iKU29WbHF3Vqm440exaYmVbxfrEfRhd-oXl25ZhOUxc&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0ETm1HSUSt367HbatCJ57a1M5iKU29WbHF3Vqm440exaYmVbxfrEfRhd-oXl25ZhOUxc&usqp=CAU"
        )
        override suspend fun uploadAvatar(file: MultipartBody.Part): Result<String> {
            delay(2500)
            return Result.success(urls.random())
        }

        override suspend fun updateName(name: String): Result<Unit> {
            delay(1500)
            return Result.success(Unit)
        }
    }
}