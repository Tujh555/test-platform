package com.example.test_platform.presentation.screens.main.quizzes

import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import javax.inject.Inject

class QuizzesTabModel @Inject constructor() :
    StateModel<QuizzesTab.Action, QuizzesTab.State>,
    StateHolder<QuizzesTab.State> by StateHolder(QuizzesTab.State()) {

    override fun onAction(action: QuizzesTab.Action) {

    }
}