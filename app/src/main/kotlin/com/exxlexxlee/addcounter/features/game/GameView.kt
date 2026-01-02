package com.exxlexxlee.addcounter.features.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.ui.common.Icons
import com.exxlexxlee.addcounter.ui.common.ListEmptyView
import com.exxlexxlee.addcounter.ui.common.ScreenMessageWithAction
import com.exxlexxlee.domain.model.UiState

@Composable
fun GameView(gameViewState: GameViewState, eventHandler: (Event) -> Unit) {

    val orientation = LocalConfiguration.current.orientation
    when (gameViewState.state) {

        UiState.Idle -> {
            val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
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
                    columns = GridCells.Fixed(
                        getGridColumns(
                            isLandscape,
                            gameViewState.activeCounts.size
                        )
                    ),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(gameViewState.activeCounts) { count ->
                        CountCard(
                            iconRes = count.icon,
                            name = count.name,
                            current = count.current.toString(),
                            tint = colorResource(Icons.tint[count.color].first),
                            background = colorResource(Icons.tint[count.color].second),
                            onClick = { eventHandler.invoke(Event.Increment(count)) },
                            onDecrement = { eventHandler.invoke(Event.Decrement(count)) },
                        )
                    }
                }

            }
        }

        UiState.Loading -> {}
    }
}

private fun getGridColumns(isLandscape: Boolean, activeCounts: Int): Int {
    val columns = if (isLandscape) when (activeCounts) {
        in 0..1 -> 1
        in 2..4 -> 2
        in 5..6 -> 3
        else -> 4
    } else when (activeCounts) {
        in 0..5 -> 1
        in 6..12 -> 2
        else -> 3
    }
    return columns
}