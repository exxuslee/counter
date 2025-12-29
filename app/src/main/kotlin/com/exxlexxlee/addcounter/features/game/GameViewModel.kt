package com.exxlexxlee.addcounter.features.game

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.game.models.Action
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.model.UiState
import com.exxlexxlee.domain.usecases.PlayersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<GameViewState, Action, Event>(
    initialState = GameViewState()
) {

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
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            Event.AddBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction =
                        Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.save(player.copy(current = player.current + player.increment))
                }
            }

            Event.AddLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction =
                        Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.save(player.copy(start = player.start + player.increment))
                }
            }

            Event.SubBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction =
                        Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.save(player.copy(current = player.current - player.increment))
                }
            }

            Event.SubLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction =
                        Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.save(player.copy(start = player.start - player.increment))
                }
            }

            is Event.SelectPlayer -> {
                viewState = viewState.copy(selectedPlayerId = viewEvent.id)
            }

            is Event.AddCount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.save(viewEvent.count)
                }
                viewAction = Action.ShowSelectPlayerMessage(R.string.add_player_toast_message)
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer
        }

    }

}