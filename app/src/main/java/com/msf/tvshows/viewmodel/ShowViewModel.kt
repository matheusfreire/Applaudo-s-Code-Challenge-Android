package com.msf.tvshows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.usecase.ShowListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowViewModel(private val listUseCase: ShowListUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var selectedFilter: FilterType = FilterType.TOP_RATED
        private set

    init {
        fetchShowList(FilterType.TOP_RATED)
    }

    fun fetchShowList(filter: FilterType, page: Int = 1, shouldLoading: Boolean = true) {
        selectedFilter = filter
        if (shouldLoading) {
            _uiState.value = UiState.Loading(true)
        }
        viewModelScope.launch {
            listUseCase(
                scope = viewModelScope,
                params = filter.filterName,
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
