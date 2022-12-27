package com.msf.tvshows.viewmodel

sealed class UiState {
    data class Loading(val loading: Boolean) : UiState()
    data class Loaded<out T>(val value: T) : UiState()
    data class Error(val message: String) : UiState()
    object Empty : UiState()
}
