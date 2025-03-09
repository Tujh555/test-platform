package com.example.test_platform.data.user

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.data.map
import com.example.test_platform.data.retrofit.toRequestBody
import com.example.test_platform.data.store.Store
import com.example.test_platform.data.user.rest.ProfileApi
import com.example.test_platform.domain.user.ProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val store: Store<UserDto>,
    private val api: ProfileApi,
    @ApplicationContext private val context: Context,
    scope: CoroutineScope
) : ProfileRepository {
    private val user = store.data.stateIn(scope, SharingStarted.Eagerly, null)

    override suspend fun updateName(name: String) = api.updateName(name).onSuccess {
        user
            .value
            ?.copy(name = name)
            ?.let { store.update(it) }
    }

    override suspend fun uploadAvatar(uri: Uri): Result<Unit> {
        val part = runCatching {
            val (name, size, type) = uri.meta()
            val body = context
                .contentResolver
                .openInputStream(uri)!!
                .toRequestBody(type?.toMediaTypeOrNull(), size)

            MultipartBody.Part.createFormData("avatar", name.orEmpty(), body)
        }.getOrNull()

        if (part == null) {
            return Result.failure(IllegalArgumentException())
        }

        return api
            .uploadAvatar(part)
            .also { Log.d("--tag", "--> url = $it") }
            .onSuccess { url -> user.value?.copy(avatar = url.url)?.let { store.update(it) } }
            .map()
    }

    private fun Uri.meta(): Triple<String?, Long?, String?> {
        val error: Triple<String?, Long?, String?> = Triple(null, null, null)

        val resolver = context.contentResolver ?: return error
        val result = runCatching {
            resolver.query(this, null, null, null)?.use { cursor ->
                val name = cursor
                    .getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    .let(cursor::getStringOrNull)
                val size = cursor
                    .getColumnIndexOrThrow(OpenableColumns.SIZE)
                    .let(cursor::getLongOrNull)

                Triple(name, size, resolver.getType(this))
            }
        }

        return result.getOrDefault(error) ?: error
    }
}