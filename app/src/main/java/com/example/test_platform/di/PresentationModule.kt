package com.example.test_platform.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.example.test_platform.presentation.screens.signin.SignInModel
import com.example.test_platform.presentation.screens.signup.SignUpModel
import com.example.test_platform.presentation.screens.splash.SplashModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
interface PresentationModule {
    @Binds
    @IntoMap
    @ScreenModelKey(SplashModel::class)
    fun splash(model: SplashModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SignInModel::class)
    fun signIn(model: SignInModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SignUpModel::class)
    fun singUp(model: SignUpModel): ScreenModel
}