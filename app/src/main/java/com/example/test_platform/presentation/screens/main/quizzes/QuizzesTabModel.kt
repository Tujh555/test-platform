package com.example.test_platform.presentation.screens.main.quizzes

import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.paging.paginator.LoadState
import com.example.test_platform.domain.paging.source.PagedElements
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.uc.GetQuizzes
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.components.Stub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizzesTabModel @Inject constructor(
    getQuizzes: GetQuizzes
) : StateModel<QuizzesTab.Action, QuizzesTab.State>,
    StateHolder<QuizzesTab.State> by StateHolder(QuizzesTab.State()) {

    private val pager = getQuizzes.own()

    init {
        observeQuizzes()
    }

    override fun onAction(action: QuizzesTab.Action) {
        when (action) {
            QuizzesTab.Action.Refresh -> {
                update { state -> state.copy(stub = Stub.Loading) }

                screenModelScope.launch {
                    pager.refresh()
                }
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