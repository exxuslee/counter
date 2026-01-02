package com.exxlexxlee.addcounter.features.settings.main.models

import com.exxlexxlee.domain.model.Count

data class ViewState(
    val isDark: Boolean = false,
    val isSound: Boolean = false,
    val isTermsOfUseRead: Boolean = false,
    val counts: List<Count> = emptyList(),
    val revealedId: Int = -1,
)