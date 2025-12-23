package com.exxlexxlee.addcounter.features.game

import android.content.res.Configuration
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.domain.model.UiState
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.navigation.BottomNavigationBar
import com.exxlexxlee.addcounter.ui.common.ListEmptyView
import com.exxlexxlee.addcounter.ui.common.ScreenMessageWithAction
import com.exxlexxlee.addcounter.R

@Composable
fun GameView(gameViewState: GameViewState, eventHandler: (Event) -> Unit) {

    when (gameViewState.state) {

        UiState.Idle -> {
            val view = LocalView.current
            val isLandscape =
                LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

                if (gameViewState.allPlayers == 0) ScreenMessageWithAction(
                    text = stringResource(R.string.game_empty_players_list),
                    icon = R.drawable.outline_empty_dashboard_24,
                ) {
                    Button(onClick = {
                        eventHandler.invoke(Event.DialogAddPlayer)
                    }) {
                        Text(stringResource(R.string.add_player))
                    }
                }

                if (gameViewState.activeCounts.isEmpty()) ListEmptyView(
                    text = stringResource(R.string.game_empty_active_players_list),
                    icon = R.drawable.outline_empty_dashboard_24,
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(if (isLandscape) 2 else 1),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(gameViewState.activeCounts) { player ->
                        PlayerCard(
                            iconRes = player.icon,
                            name = player.name,
                            level = player.start.toString(),
                            bonus = player.current.toString(),
                            life = (player.start + player.current).toString(),
                            selected = player.id == gameViewState.selectedPlayerId,
                            onSelectRow = {
                                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                                eventHandler.invoke(Event.SelectPlayer(player.id))
                            },
                            onSelectIcon = {
                                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            },
                        )
                    }
                }

                if (!isLandscape) BottomNavigationBar { event ->
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    eventHandler(event)
                }
            }
        }

        UiState.Loading -> {}
    }
}