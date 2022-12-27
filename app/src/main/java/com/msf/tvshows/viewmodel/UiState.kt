package com.msf.tvshows.viewmodel

import com.msf.tvshows.model.Show

sealed class UiState {
    data class Loading(val loading: Boolean) : UiState()
    data class Loaded(val shows: List<Show>) : UiState()
    data class Error(val message: String) : UiState()
    object Empty : UiState()
}
