package com.exxlexxlee.addcounter.di


import com.exxlexxlee.addcounter.features.game.GameViewModel
import com.exxlexxlee.addcounter.features.root.MainViewModel
import com.exxlexxlee.addcounter.features.settings.donate.DonateViewModel
import com.exxlexxlee.addcounter.features.settings.language.LanguageViewModel
import com.exxlexxlee.addcounter.features.settings.main.SettingsViewModel
import com.exxlexxlee.addcounter.features.settings.terms.TermsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { MainViewModel(get()) }

    viewModel { GameViewModel(get(), get()) }

    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { DonateViewModel(get()) }
    viewModel { LanguageViewModel() }
    viewModel { TermsViewModel(get()) }
}