package com.example.test_platform.presentation.screens.main.search

import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.paging.paginator.LoadState
import com.example.test_platform.domain.paging.source.PagedElements
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.uc.GetQuizzes
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.components.Stub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTabModel @Inject constructor(
    getQuizzes: GetQuizzes
) : StateModel<SearchTab.Action, SearchTab.State>,
    StateHolder<SearchTab.State> by StateHolder(SearchTab.State()) {

    private val search = state.map { it.query }.distinctUntilChanged()
    private val pager = getQuizzes.search(minDelay = 600, searchQuery = search)

    init {
        observeQuizzes()
    }

    override fun onAction(action: SearchTab.Action) {
        when (action) {
            is SearchTab.Action.Query -> update { state -> state.copy(query = action.value) }
            is SearchTab.Action.Refresh -> screenModelScope.launch {
                if (action.silent.not()) {
                    update { state -> state.copy(stub = Stub.Loading) }
                }
                pager.refresh()
            }
        }
    }

    private fun observeQuizzes() {
        pager
            .paginate(state.value.lastQuizIndex)
            .onEach { elements ->
                update { state ->
                    state.copy(
                        quizzes = elements.elements,
                        stub = elements.stub(),
                        adding = elements.loadState == LoadState.Loading
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(screenModelScope)
    }

    private fun PagedElements<Quiz>.stub(): Stub {
        if (state.value.stub == Stub.Loaded) {
            return Stub.Loaded
        }

        return when (loadState) {
            LoadState.Failed -> Stub.Error
            LoadState.Loaded, LoadState.Initial -> if (elements.isEmpty()) {
                Stub.Empty
            } else {
                Stub.Loaded
            }
            LoadState.Loading -> Stub.Loading
        }
    }
}