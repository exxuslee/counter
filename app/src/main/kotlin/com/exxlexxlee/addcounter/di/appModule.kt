package com.exxlexxlee.addcounter.di

import com.exxlexxlee.data.di.dataModule
import com.exxlexxlee.domain.di.domainModule
import com.exxlexxlee.addcounter.managers.PuzzleWorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { PuzzleWorkManager(androidContext()) }

    includes(presentationModule)
    includes(domainModule)
    includes(dataModule)

}