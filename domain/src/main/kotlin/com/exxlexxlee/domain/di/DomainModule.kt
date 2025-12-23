package com.exxlexxlee.domain.di

import com.exxlexxlee.domain.usecases.PlayersUseCase
import com.exxlexxlee.domain.usecases.PriceUseCase
import com.exxlexxlee.domain.usecases.SettingsUseCase
import com.exxlexxlee.domain.usecases.ThemeController
import org.koin.dsl.module

val domainModule = module {
    single<ThemeController> { ThemeController.Base(get()) }
    single<SettingsUseCase> { SettingsUseCase.Base(get()) }
    single<PlayersUseCase> { PlayersUseCase.Base(get()) }
    single<PriceUseCase> { PriceUseCase.Base(get()) }

}