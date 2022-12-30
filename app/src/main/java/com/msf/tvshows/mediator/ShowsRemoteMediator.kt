package com.msf.tvshows.mediator

import androidx.annotation.VisibleForTesting
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.msf.tvshows.database.ShowDatabase
import com.msf.tvshows.model.list.RemoteKeys
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.network.TvShowService
import com.msf.tvshows.viewmodel.FilterType
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ShowsRemoteMediator(
    private val moviesApiService: TvShowService,
    private val showDatabase: ShowDatabase
) : RemoteMediator<Int, Show>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var actualPage = 1

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var lastFilter: FilterType = FilterType.TOP_RATED

    fun setFilterType(filterType: FilterType) {
        if (lastFilter != filterType) {
            actualPage = 1
        }
        lastFilter = filterType
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val lastCreationTime = showDatabase.getRemoteKeysDao().getCreationTime() ?: 0
        return if (System.currentTimeMillis() - lastCreationTime  < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Show>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        try {
            val apiResponse = moviesApiService.callShowsFiltered(lastFilter.filterName, page = page)
            val movies = apiResponse.shows
            val endOfPaginationReached = movies.isEmpty()
            showDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    showDatabase.getRemoteKeysDao().clearRemoteKeys()
                    showDatabase.getShowsDao().clearAllShows()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeys(showId = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }
                showDatabase.getRemoteKeysDao().insertAll(remoteKeys)
                showDatabase.getShowsDao().insertAll(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Show>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                showDatabase.getRemoteKeysDao().getRemoteKeyByShowID(id)
            }
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Show>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { show ->
            showDatabase.getRemoteKeysDao().getRemoteKeyByShowID(show.id)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Show>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { show ->
            showDatabase.getRemoteKeysDao().getRemoteKeyByShowID(show.id)
        }
    }
}
