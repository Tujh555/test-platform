package com.example.test_platform.domain.paging.paginator

sealed interface LoadState {
    data object Initial : LoadState
    data object Loading : LoadState
    data object Loaded : LoadState
    data object Failed : LoadState
}