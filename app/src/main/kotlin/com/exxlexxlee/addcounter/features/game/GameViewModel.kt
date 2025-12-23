package com.exxlexxlee.addcounter.features.game
import androidx.lifecycle.viewModelScope
import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.model.UiState
import com.exxlexxlee.domain.usecases.PlayersUseCase
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.game.models.Action
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
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
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.update(player.copy(current = player.current + 1))
                }
            }

            Event.AddLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.update(player.copy(start = player.start + 1))
                }
            }

            Event.SubBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.update(player.copy(current = player.current - 1))
                }
            }

            Event.SubLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activeCounts.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.count(id) ?: return@launch
                    playersUseCase.update(player.copy(start = player.start - 1))
                }
            }

            is Event.SelectPlayer -> {
                viewState = viewState.copy(selectedPlayerId = viewEvent.id)
            }

            is Event.SwitchSex -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.update(
                        viewEvent.count.copy(
                            reverseSex = !viewEvent.count.reverseSex
                        )
                    )
                }
            }

            is Event.AddPlayer ->  {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.save(Count(name = viewEvent.name, icon = viewEvent.icon))
                }
                viewAction = Action.ShowSelectPlayerMessage(R.string.add_player_toast_message)
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer
        }

    }

}