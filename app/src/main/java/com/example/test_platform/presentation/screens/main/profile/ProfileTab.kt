package com.example.test_platform.presentation.screens.main.profile

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.R
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.TabComponent

object ProfileTab : TabComponent<ProfileTab.Action, ProfileTab.State> {
    @Immutable
    data class State @OptIn(ExperimentalMaterial3Api::class) constructor(
        val id: String = "",
        val avatar: String? = null,
        val name: String = "",
        val finishVisible: Boolean = false,
        val ptr: PullToRefreshState = PullToRefreshState(
            enabled = { true },
            positionalThresholdPx = 0f
        )
    )

    @Immutable
    sealed interface Action {
        @JvmInline
        value class Name(val value: String) : Action

        @JvmInline
        value class UploadAvatar(val uri: Uri) : Action

        data object Save : Action
    }

    override val title: String = "Profile"
    override val icons: IconPair = IconPair(
        selected = R.drawable.ic_profile_selected,
        unselected = R.drawable.ic_profile_unselected
    )

    @Composable
    @NonRestartableComposable
    override fun Content(state: State, onAction: (Action) -> Unit) =
        ProfileTabContent(state, onAction)

    @Composable
    override fun model(): ProfileTabModel = getScreenModel()

    private fun readResolve(): Any = ProfileTab
}