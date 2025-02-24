package com.example.test_platform.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

interface ScreenComponent<A, S, E> : Screen {
    @Composable
    override fun Content() {
        val model = model()
        val state by model.state.collectAsState()
        val event by model.event.collectAsState(null)

        Content(state) { model.onAction(it) }
        event?.let { Event(it) }
    }

    @Composable
    fun Content(state: S, onAction: (A) -> Unit)

    @Composable
    fun Event(event: E)

    @Composable
    fun model(): Model<A, S, E>
}

interface Model<A, S, E> : ScreenModel {
    val ioScope: CoroutineScope
        get() = ScreenModelStore.getOrPutDependency(
            screenModel = this,
            name = "ScreenModelIoScope",
            onDispose = { scope -> scope.cancel() },
            factory = { _ -> CoroutineScope(SupervisorJob() + Dispatchers.IO) }
        )
    val state: StateFlow<S>
    val event: Flow<E>

    fun onAction(action: A)
}

abstract class BaseModel<A, S, E>(initial: S) : Model<A, S, E> {
    protected val _state = MutableStateFlow(initial)
    protected val _event = Channel<E>(Channel.UNLIMITED)
    override val state = _state.asStateFlow()
    override val event = _event.consumeAsFlow()

    abstract override fun onAction(action: A)

    override fun onDispose() {
        _event.close()
    }
}