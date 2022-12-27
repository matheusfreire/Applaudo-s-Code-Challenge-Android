package com.msf.tvshows.usecase

import com.msf.tvshows.core.CoroutinesContextProvider
import com.msf.tvshows.core.Either
import com.msf.tvshows.core.RequestWrapper
import com.msf.tvshows.core.UseCase
import com.msf.tvshows.model.Show
import com.msf.tvshows.model.ShowResponse
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.repository.ShowRepository

class ShowListUseCase(
    private val repository: ShowRepository,
    contextProvider: CoroutinesContextProvider,
    private val requestWrapper: RequestWrapper
) : UseCase<ResultWrapper<List<Show>>, String>(contextProvider) {
    override suspend fun run(params: String): Either<ResultWrapper<List<Show>>, Throwable> =
        requestWrapper.wrapper {
            when (val result = repository.fetchShowList(params)) {
                is ResultWrapper.Success -> handleSuccess(result)
                is ResultWrapper.GenericError -> result
                else -> ResultWrapper.NetworkError
            }
        }

    private fun handleSuccess(result: ResultWrapper.Success<ShowResponse>): ResultWrapper<List<Show>> {
        return if (result.value.totalResults > 0) {
            ResultWrapper.Success(result.value.results)
        } else {
            ResultWrapper.GenericError(404, "No tv shows founded with your criteria")
        }
    }
}
