package com.msf.tvshows.core

interface RequestWrapper {
    suspend fun <D> wrapper(call: suspend () -> D): Either<D, Throwable>
}
