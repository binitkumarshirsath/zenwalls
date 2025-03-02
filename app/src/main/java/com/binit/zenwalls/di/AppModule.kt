package com.binit.zenwalls.di

import com.binit.zenwalls.data.network.HttpClientFactory
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.domain.repository.WallpaperRepositoryImpl
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreenViewModel
import com.binit.zenwalls.ui.screens.wallpaper_list.HomeScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClientFactory.create(engine = CIO.create())
    }.bind(HttpClient::class)


    single{
        WallpaperRepositoryImpl(get())
    }.bind(WallpaperRepository::class)

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::WallpaperScreenViewModel)
}