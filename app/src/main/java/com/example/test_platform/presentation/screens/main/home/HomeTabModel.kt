package com.example.test_platform.presentation.screens.main.home

import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test_platform.domain.paging.paginator.LoadState
import com.example.test_platform.domain.paging.source.PagedElements
import com.example.test_platform.domain.test.Quiz
import com.example.test_platform.domain.test.uc.GetQuizzes
import com.example.test_platform.domain.user.ReactiveUser
import com.example.test_platform.presentation.base.StateHolder
import com.example.test_platform.presentation.base.StateModel
import com.example.test_platform.presentation.components.Stub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeTabModel @Inject constructor(
    private val reactiveUser: ReactiveUser,
    getQuizzes: GetQuizzes
) : StateModel<HomeTab.Action, HomeTab.State>,
    StateHolder<HomeTab.State> by StateHolder(HomeTab.State(reactiveUser.value!!)) {

    private val allPager = getQuizzes.all()
    private val ownPager = getQuizzes.own()

    init {
        observeUser()
        observeAll()
        observeOwn()
    }

    override fun onAction(action: HomeTab.Action) {
        when (action) {
            HomeTab.Action.RefreshAll -> screenModelScope.launch {
                update { state -> state.copy(allStub = Stub.Loading) }
                delay(500)
                allPager.refresh()
            }
            HomeTab.Action.RefreshOwn -> screenModelScope.launch {
                update { state -> state.copy(ownStub = Stub.Loading) }
                delay(500)
                ownPager.refresh()
            }
        }
    }

    private fun observeOwn() {
        ownPager
            .paginate(flowOf(0))
            .onEach { elements ->
                update { state ->
                    state.copy(ownStub = elements.stub(), ownQuizzes = elements.elements)
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(screenModelScope)
    }

    private fun observeAll() {
        allPager
            .paginate(flowOf(0))
            .onEach { elements ->
                update { state ->
                    state.copy(allStub = elements.stub(), allQuizzes = elements.elements)
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(screenModelScope)
    }

    private fun observeUser() {
        reactiveUser
            .onEach { user ->
                if (user != null) {
                    update { it.copy(user = user) }
                }
            }
            .launchIn(screenModelScope)
    }

    private fun PagedElements<Quiz>.stub() = when (loadState) {
        LoadState.Failed -> Stub.Error
        LoadState.Loaded, LoadState.Initial -> if (elements.isEmpty()) {
            Stub.Empty
        } else {
            Stub.Loaded
        }
        LoadState.Loading -> Stub.Loading
    }
}