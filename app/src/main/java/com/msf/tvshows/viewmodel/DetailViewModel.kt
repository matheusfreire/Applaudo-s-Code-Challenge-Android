package com.msf.tvshows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.usecase.DetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val detailUseCase: DetailUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun getDetail(id: Long) {
        viewModelScope.launch {
            detailUseCase(
                scope = viewModelScope,
                params = id,
                onError = { throwable ->
                    val message = throwable.message ?: ""
                    _uiState.value = UiState.Error(message)
                },
                onSuccess = { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = UiState.Loaded(result.value)
                        }
                        is ResultWrapper.GenericError -> _uiState.value = UiState.Error(result.error ?: "")
                        else -> _uiState.value = UiState.Empty
                    }
                }
            )
        }
    }
}
