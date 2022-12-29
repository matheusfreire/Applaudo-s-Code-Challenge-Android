package com.msf.tvshows.core

import com.msf.tvshows.network.ResultWrapper
import retrofit2.HttpException
import java.io.IOException

class RequestWrapperImpl : RequestWrapper {
    override suspend fun <D> wrapper(call: suspend () -> D): Either<D, Throwable> {
        return try {
            when (val dataFromRemote = call()) {
                is ResultWrapper.GenericError -> throw Exception(dataFromRemote.error)
                is ResultWrapper.Success<*> -> Either.Success(dataFromRemote)
                else -> throw IllegalStateException("Network Error")
            }
        } catch (httpException: HttpException) {
            return Either.Failure(httpException)
        } catch (ioException: IOException) {
            Either.Failure(ioException)
        } catch (stateException: IllegalStateException) {
            Either.Failure(stateException)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}
