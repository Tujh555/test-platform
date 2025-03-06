package com.example.test_platform.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.example.test_platform.presentation.screens.main.home.HomeTabModel
import com.example.test_platform.presentation.screens.main.profile.ProfileTabModel
import com.example.test_platform.presentation.screens.main.quizzes.QuizzesTabModel
import com.example.test_platform.presentation.screens.main.search.SearchTabModel
import com.example.test_platform.presentation.screens.quiz.create.QuizCreateModel
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

    @Binds
    @IntoMap
    @ScreenModelKey(HomeTabModel::class)
    fun home(model: HomeTabModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(ProfileTabModel::class)
    fun profile(model: ProfileTabModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(QuizzesTabModel::class)
    fun quizzes(model: QuizzesTabModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SearchTabModel::class)
    fun search(model: SearchTabModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(QuizCreateModel::class)
    fun create(model: QuizCreateModel): ScreenModel
}