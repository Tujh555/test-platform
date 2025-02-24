package com.example.test_platform.presentation.screens.signin

import com.example.test_platform.domain.auth.AuthRepository
import com.example.test_platform.presentation.base.BaseStateModel
import javax.inject.Inject

class SignInModel @Inject constructor(
    private val repository: AuthRepository
) : BaseStateModel<SignInScreen.Action, SignInScreen.State>(SignInScreen.State()) {
    override fun onAction(action: SignInScreen.Action) {
        TODO("Not yet implemented")
    }
}