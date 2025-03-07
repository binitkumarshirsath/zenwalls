package com.binit.zenwalls.di

import com.binit.zenwalls.data.network.HttpClientFactory
import com.binit.zenwalls.data.repository.DownloadRepoImpl
import com.binit.zenwalls.data.repository.NetworkConnectivityObserverImpl
import com.binit.zenwalls.data.repository.WallpaperRepositoryImpl
import com.binit.zenwalls.domain.repository.DownloadRepository
import com.binit.zenwalls.domain.repository.NetworkConnectivityObserver
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.ui.screens.favourite.FavouriteScreenViewModel
import com.binit.zenwalls.ui.screens.search.SearchScreenViewModel
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreenViewModel
import com.binit.zenwalls.ui.screens.wallpaper_list.HomeScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClientFactory.create(engine = CIO.create())
    }.bind(HttpClient::class)


    single {
        WallpaperRepositoryImpl(get())
    }.bind(WallpaperRepository::class)

    single {
        DownloadRepoImpl(get())
    }.bind(DownloadRepository::class)

    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    single {
        NetworkConnectivityObserverImpl(
            get(),
            get()
        )
    }.bind(NetworkConnectivityObserver::class)


    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::WallpaperScreenViewModel)
    viewModelOf(::SearchScreenViewModel)
    viewModelOf(::FavouriteScreenViewModel)
}