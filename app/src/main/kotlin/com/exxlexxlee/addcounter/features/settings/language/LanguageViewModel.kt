package com.exxlexxlee.addcounter.features.settings.language


import com.exxlexxlee.addcounter.features.settings.language.models.Action
import com.exxlexxlee.addcounter.features.settings.language.models.Event
import com.exxlexxlee.addcounter.features.settings.language.models.ViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel

class LanguageViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> viewAction = Action.SetLocale(viewEvent.type)
        }

    }

}