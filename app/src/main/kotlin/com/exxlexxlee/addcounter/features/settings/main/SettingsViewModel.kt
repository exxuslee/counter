package com.exxlexxlee.addcounter.features.settings.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.usecases.PlayersUseCase
import com.exxlexxlee.domain.usecases.SettingsUseCase
import com.exxlexxlee.domain.usecases.ThemeController
import com.exxlexxlee.addcounter.features.settings.main.models.Action
import com.exxlexxlee.addcounter.features.settings.main.models.Event
import com.exxlexxlee.addcounter.features.settings.main.models.ViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeController: ThemeController,
    private val settingsUseCase: SettingsUseCase,
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                themeController.isDark,
                settingsUseCase.isTermsOfUseRead,
                playersUseCase.counts,
            ) { isDark, isTermsOfUseRead, players ->
                ViewState(
                    isTermsOfUseRead = isTermsOfUseRead,
                    isDark = isDark,
                    counts = players
                )
            }.collect { newState ->
                viewState = newState
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            is Event.IsDark -> {
                themeController.setDark(viewEvent.newValue)
                viewState = viewState.copy(isDark = viewEvent.newValue)
            }

            Event.DialogNewGame -> viewAction = Action.NewGame

            Event.ConfirmNewGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.counts.value.forEach { player ->
                        val resetPlayer = player.copy(
                            start = 1,
                            current = 0,
                            reverseSex = false,
                        )
                        playersUseCase.update(resetPlayer)
                    }
                    delay(500)
                    viewAction = Action.PopBack
                }
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer

            is Event.AddPlayer -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.save(Count(name = viewEvent.name, icon = viewEvent.icon))
                }
                clearAction()

            }

            is Event.ActivatePlayer -> viewModelScope.launch(Dispatchers.IO) {
                playersUseCase.update(viewEvent.count.copy(active = !viewEvent.count.active))
            }

            is Event.Reveal -> viewState = viewState.copy(revealedId = viewEvent.id)

            is Event.DeletePlayer -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.delete(viewEvent.id)
                }
            }
        }

    }

}