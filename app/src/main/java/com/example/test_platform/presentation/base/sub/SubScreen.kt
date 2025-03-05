package com.example.test_platform.presentation.base.sub

import cafe.adriel.voyager.core.screen.Screen

interface SubScreen<S> : Screen {
    val state: S
}