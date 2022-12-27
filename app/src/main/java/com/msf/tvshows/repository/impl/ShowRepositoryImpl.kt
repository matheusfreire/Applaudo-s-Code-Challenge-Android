package com.msf.tvshows.repository.impl

import com.msf.tvshows.model.ShowResponse
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.network.TvShowService
import com.msf.tvshows.repository.ShowRepository
import retrofit2.HttpException

class ShowRepositoryImpl(private val service: TvShowService) : ShowRepository {
    override suspend fun fetchShowList(filter: String): ResultWrapper<ShowResponse> = try {
        val showsResponse = service.callShowsFiltered(filter, "d491c13d0f83cf3eb7d60cf61339b370")
        ResultWrapper.Success(showsResponse)
    } catch (e: HttpException) {
        ResultWrapper.GenericError(e.code(), e.message())
    } catch (e: Exception) {
        ResultWrapper.GenericError(999, "Something weird happens")
    }

    override suspend fun getShowDetail(id: String) {
    }
}
