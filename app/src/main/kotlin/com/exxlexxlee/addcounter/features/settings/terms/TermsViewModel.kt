package com.exxlexxlee.addcounter.features.settings.terms

import com.exxlexxlee.domain.usecases.SettingsUseCase
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import com.exxlexxlee.addcounter.features.settings.terms.models.Action
import com.exxlexxlee.addcounter.features.settings.terms.models.Event
import com.exxlexxlee.addcounter.features.settings.terms.models.ViewState

class TermsViewModel(
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        isTermsOfUseRead = settingsUseCase.isTermsOfUseRead()
    )
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.ReadTerms -> {
                settingsUseCase.isTermsOfUseRead(true)
                viewState = viewState.copy(isTermsOfUseRead = true)
            }
        }

    }

}