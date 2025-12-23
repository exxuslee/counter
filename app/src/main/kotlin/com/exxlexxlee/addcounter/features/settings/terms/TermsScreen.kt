package com.exxlexxlee.addcounter.features.settings.terms

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(
    viewModel: TermsViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()


    TermsView(viewState) {
        viewModel.obtainEvent(it)
    }

}
