package com.exxlexxlee.addcounter.features.settings.terms.models

sealed class Event {
    data object ReadTerms: Event()
}