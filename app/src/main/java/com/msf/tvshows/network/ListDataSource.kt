package com.msf.tvshows.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.msf.tvshows.model.list.Show
import retrofit2.HttpException
import java.io.IOException

class ListDataSource(
    private val service: TvShowService,
) : PagingSource<Int, Show>() {

    companion object {
        private const val PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        return try {
            val result: List<Show> = params.key?.let { service.callShowsFiltered("", "", it).results } ?: listOf()
            LoadResult.Page(
                data = result,
                prevKey = params.key,
                nextKey = params.key?.plus(1) ?: PAGE.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
