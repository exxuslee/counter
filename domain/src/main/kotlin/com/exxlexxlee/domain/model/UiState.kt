package com.exxlexxlee.domain.model

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
}