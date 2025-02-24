package com.example.test_platform.data

import android.content.Context
import com.example.test_platform.data.auth.AuthRepositoryImpl
import com.example.test_platform.data.dto.UserDto
import com.example.test_platform.data.store.Store
import com.example.test_platform.data.store.jsonStore
import com.example.test_platform.data.store.stringStore
import com.example.test_platform.data.user.UserFlow
import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.domain.user.ReactiveUser
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun auth(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun user(flow: UserFlow): ReactiveUser

    companion object {
        @Provides
        @Singleton
        fun ioScope() = CoroutineScope(
            Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }
        )

        @Provides
        @Singleton
        fun userStore(@ApplicationContext context: Context): Store<UserDto> {
            return jsonStore<UserDto>(context, "user")
        }

        @Provides
        @Singleton
        fun authTokenStore(@ApplicationContext context: Context): Store<String> {
            return stringStore(context, "auth_token")
        }
    }
}