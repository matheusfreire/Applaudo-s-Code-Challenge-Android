package com.msf.tvshows.di

import com.msf.tvshows.core.CoroutinesContextProvider
import com.msf.tvshows.core.RequestWrapper
import com.msf.tvshows.core.RequestWrapperImpl
import com.msf.tvshows.mediator.ShowsRemoteMediator
import com.msf.tvshows.network.ListDataSource
import com.msf.tvshows.usecase.DetailUseCase
import com.msf.tvshows.viewmodel.DetailViewModel
import com.msf.tvshows.viewmodel.ShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ShowDI {
    val modules = module {
        single { CoroutinesContextProvider() }
        factory<RequestWrapper> { RequestWrapperImpl() }
        single {
            ListDataSource(
                showRepository = get()
            )
        }
        single {
            ShowsRemoteMediator(
                moviesApiService = get(),
                showDatabase = get()
            )
        }
        single {
            DetailUseCase(
                repository = get(),
                contextProvider = get(),
                requestWrapper = get()
            )
        }
        viewModel {
            ShowViewModel(
                remoteMediator = get(),
                showDatabase = get()
            )
        }
        viewModel { DetailViewModel(detailUseCase = get()) }
    }
}
