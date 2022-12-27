package com.msf.tvshows.usecase

import com.msf.tvshows.core.CoroutinesContextProvider
import com.msf.tvshows.core.Either
import com.msf.tvshows.core.RequestWrapper
import com.msf.tvshows.core.UseCase
import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.repository.ShowRepository

class DetailUseCase(
    private val repository: ShowRepository,
    contextProvider: CoroutinesContextProvider,
    private val requestWrapper: RequestWrapper
) : UseCase<ResultWrapper<DetailResponse>, Long>(contextProvider) {
    override suspend fun run(params: Long): Either<ResultWrapper<DetailResponse>, Throwable> =
        requestWrapper.wrapper {
            when (val result = repository.getShowDetail(params)) {
                is ResultWrapper.Success -> ResultWrapper.Success(result.value)
                is ResultWrapper.GenericError -> result
                else -> ResultWrapper.NetworkError
            }
        }
}
