package com.example.test_platform.presentation.screens.main.search

import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import javax.inject.Inject

class SearchTabModel @Inject constructor() :
    StateModel<SearchTab.Action, SearchTab.State>,
    StateHolder<SearchTab.State> by StateHolder(SearchTab.State()) {

    override fun onAction(action: SearchTab.Action) {

    }

}