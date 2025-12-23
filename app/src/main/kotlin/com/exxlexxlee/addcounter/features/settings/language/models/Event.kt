package com.exxlexxlee.addcounter.features.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Event {
    data class Select(val type: SupportedLocales) : Event()
}