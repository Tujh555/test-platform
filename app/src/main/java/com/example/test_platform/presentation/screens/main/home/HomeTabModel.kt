package com.example.test_platform.presentation.screens.main.home

import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import javax.inject.Inject

class HomeTabModel @Inject constructor() :
    StateModel<HomeTab.Action, HomeTab.State>,
    StateHolder<HomeTab.State> by StateHolder(HomeTab.State()) {

    override fun onAction(action: HomeTab.Action) {

    }
}