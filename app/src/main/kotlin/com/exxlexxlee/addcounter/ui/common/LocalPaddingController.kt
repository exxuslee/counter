package com.exxlexxlee.addcounter.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf

val LocalPaddingController = staticCompositionLocalOf<PaddingValues> {
    error("NavController not provided")
}