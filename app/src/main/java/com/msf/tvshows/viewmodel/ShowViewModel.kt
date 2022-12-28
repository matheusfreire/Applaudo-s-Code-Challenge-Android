package com.msf.tvshows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.network.ListDataSource
import kotlinx.coroutines.flow.Flow

class ShowViewModel(private val listDataSource: ListDataSource) : ViewModel() {

    var selectedFilter: FilterType = FilterType.TOP_RATED
        private set

    fun fetchShowList(filter: FilterType): Flow<PagingData<Show>> {
        selectedFilter = filter
        listDataSource.setFilterType(filter)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                listDataSource
            }
        ).flow.cachedIn(viewModelScope)
    }
}
