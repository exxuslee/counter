package com.exxlexxlee.addcounter.features.settings.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.addcounter.features.settings.main.models.Action
import com.exxlexxlee.addcounter.features.settings.main.models.Event
import com.exxlexxlee.addcounter.features.settings.main.models.ViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import com.exxlexxlee.domain.usecases.PlayersUseCase
import com.exxlexxlee.domain.usecases.SettingsUseCase
import com.exxlexxlee.domain.usecases.ThemeController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal

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
                settingsUseCase.isSound,
            ) { isDark, isTermsOfUseRead, players, isSound ->
                ViewState(
                    isTermsOfUseRead = isTermsOfUseRead,
                    isDark = isDark,
                    counts = players,
                    isSound = isSound,
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
                        val resetPlayer = player.copy(start = BigDecimal.ZERO)
                        playersUseCase.save(resetPlayer)
                    }
                    delay(500)
                    viewAction = Action.PopBack
                }
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer

            is Event.AddCount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.save(viewEvent.count)
                }
                clearAction()

            }

            is Event.ActivatePlayer -> viewModelScope.launch(Dispatchers.IO) {
                playersUseCase.save(viewEvent.count.copy(active = !viewEvent.count.active))
            }

            is Event.Reveal -> viewState = viewState.copy(revealedId = viewEvent.id)

            is Event.DeletePlayer -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.delete(viewEvent.id)
                }
            }

            is Event.IsSound -> {
                settingsUseCase.setSound(viewEvent.newValue)
            }
        }

    }

}