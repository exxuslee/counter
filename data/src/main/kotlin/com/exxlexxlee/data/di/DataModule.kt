package com.exxlexxlee.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exxlexxlee.data.local.AppDatabase
import com.exxlexxlee.data.remote.cmcap.CMCapService
import com.exxlexxlee.data.remote.cmcap.api.CMCapServiceImpl
import com.exxlexxlee.data.repositories.PlayersRepositoryImpl
import com.exxlexxlee.data.repositories.PriceRepositoryImpl
import com.exxlexxlee.data.repositories.SettingsRepositoryImpl
import com.exxlexxlee.domain.repositories.PlayersRepository
import com.exxlexxlee.domain.repositories.PriceRepository
import com.exxlexxlee.domain.repositories.SettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(CIO) {
            install(ContentEncoding) {
                deflate()
                gzip()
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.NONE
            }
        }
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "DB")
            .fallbackToDestructiveMigration(false).build()
    }

    single { get<AppDatabase>().playerDAO }
    single { get<AppDatabase>().tokensDAO }

    single<CMCapService> { CMCapServiceImpl(get()) }

    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<PlayersRepository> { PlayersRepositoryImpl(get()) }
    single<PriceRepository> { PriceRepositoryImpl(get(), get(), get()) }


}