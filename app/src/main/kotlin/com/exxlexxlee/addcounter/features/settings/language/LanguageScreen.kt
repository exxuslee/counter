package com.exxlexxlee.addcounter.features.settings.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    LanguageView(viewState) {
        viewModel.obtainEvent(it)
    }
}