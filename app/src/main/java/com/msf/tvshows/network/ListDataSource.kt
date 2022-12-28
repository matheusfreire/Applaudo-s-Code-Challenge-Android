package com.msf.tvshows.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.model.list.ShowResponse
import com.msf.tvshows.repository.ShowRepository
import com.msf.tvshows.viewmodel.FilterType

class ListDataSource(
    private val showRepository: ShowRepository
) : PagingSource<Int, Show>() {

    private var actualPage = 1

    private var lastFilter: FilterType = FilterType.TOP_RATED

    fun setFilterType(filterType: FilterType) {
        if (lastFilter != filterType) {
            actualPage = 1
        }
        lastFilter = filterType
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        return try {
            val page = params.key ?: 1
            return when (val result = showRepository.fetchShowList(lastFilter.filterName, page)) {
                is ResultWrapper.Success -> handleSuccess(result, params)
                is ResultWrapper.GenericError -> throw Exception(result.error)
                is ResultWrapper.NetworkError -> throw Exception("Network")
                else -> throw Exception("Generic exception")
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun handleSuccess(
        result: ResultWrapper.Success<ShowResponse>,
        params: LoadParams<Int>
    ): LoadResult.Page<Int, Show> {
        return LoadResult.Page(
            data = result.value.results,
            prevKey = params.key,
            nextKey = params.key?.plus(1)
        )
    }
}
