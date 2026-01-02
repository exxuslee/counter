package com.exxlexxlee.addcounter.features.game

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.addcounter.features.game.models.Action
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import com.exxlexxlee.domain.model.Operator
import com.exxlexxlee.domain.model.UiState
import com.exxlexxlee.domain.usecases.PlayersUseCase
import com.exxlexxlee.domain.usecases.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val playersUseCase: PlayersUseCase,
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<GameViewState, Action, Event>(
    initialState = GameViewState()
) {
    private var isSoundEnabled: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playersUseCase.counts.collect {
                val activePlayers = it.filter { player -> player.active }
                viewState = viewState.copy(
                    activeCounts = activePlayers,
                    allPlayers = it.size,
                    state = UiState.Idle,
                )
            }
        }
        viewModelScope.launch {
            settingsUseCase.isSound.collect {
                isSoundEnabled = it
            }
        }
    }


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            is Event.Increment -> viewModelScope.launch(Dispatchers.IO) {
                val new = when (viewEvent.count.operator) {
                    Operator.ADD -> viewEvent.count.current + viewEvent.count.increment
                    Operator.SUBTRACT -> viewEvent.count.current - viewEvent.count.increment
                    Operator.MULTIPLY -> viewEvent.count.current * viewEvent.count.increment
                    Operator.DIVIDE -> viewEvent.count.current / viewEvent.count.increment
                }
                playersUseCase.save(viewEvent.count.copy(current = new))
                if (isSoundEnabled) viewAction = Action.Sound(new)
            }

            is Event.Decrement -> viewModelScope.launch(Dispatchers.IO) {
                val new = when (viewEvent.count.operator) {
                    Operator.ADD -> viewEvent.count.current - viewEvent.count.increment
                    Operator.SUBTRACT -> viewEvent.count.current + viewEvent.count.increment
                    Operator.MULTIPLY -> viewEvent.count.current / viewEvent.count.increment
                    Operator.DIVIDE -> viewEvent.count.current * viewEvent.count.increment
                }
                playersUseCase.save(viewEvent.count.copy(current = new))
                if (isSoundEnabled) viewAction = Action.Sound(new)
            }

            is Event.SelectPlayer -> {
                viewState = viewState.copy(selectedPlayerId = viewEvent.id)
            }

            is Event.AddCount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.save(viewEvent.count)
                }
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer
        }

    }

}