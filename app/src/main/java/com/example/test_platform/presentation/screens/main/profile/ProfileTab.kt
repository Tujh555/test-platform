package com.example.test_platform.presentation.screens.main.profile

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import cafe.adriel.voyager.hilt.getScreenModel
import com.example.test_platform.R
import com.example.test_platform.presentation.base.IconPair
import com.example.test_platform.presentation.base.TabComponent

class ProfileTab : TabComponent<ProfileTab.Action, ProfileTab.State> {
    @Immutable
    data class State(
        val id: String = "",
        val avatar: String? = null,
        val name: String = "",
        val avatarSending: Boolean = false,
        val nameSending: Boolean = false,
        val finishVisible: Boolean = false
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
}