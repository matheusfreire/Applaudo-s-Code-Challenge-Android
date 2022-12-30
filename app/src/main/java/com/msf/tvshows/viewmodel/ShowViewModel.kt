package com.msf.tvshows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.msf.tvshows.database.ShowDatabase
import com.msf.tvshows.mediator.ShowsRemoteMediator
import com.msf.tvshows.model.list.Show
import kotlinx.coroutines.flow.Flow

class ShowViewModel(
    private val remoteMediator: ShowsRemoteMediator,
    private val showDatabase: ShowDatabase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun fetchShowList(filter: FilterType): Flow<PagingData<Show>> {
        remoteMediator.setFilterType(filter)
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = {
                showDatabase.getShowsDao().getShows()
            },
            remoteMediator = remoteMediator
        ).flow.cachedIn(viewModelScope)
    }
}
