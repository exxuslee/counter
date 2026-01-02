package com.exxlexxlee.addcounter.features.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.addcounter.features.game.models.Action
import com.exxlexxlee.addcounter.features.game.models.Event.AddCount
import com.exxlexxlee.addcounter.managers.rememberSoundManager
import com.exxlexxlee.addcounter.ui.common.AddCountDialog
import org.koin.androidx.compose.koinViewModel
import java.math.BigInteger

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val soundManager = rememberSoundManager()

    GameView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        Action.AddPlayer -> {
            AddCountDialog(
                onDismissRequest = {
                    viewModel.clearAction()
                },
                onAdd = { count ->
                    viewModel.obtainEvent(AddCount(count))
                }
            )
        }

        is Action.Sound -> {
            viewModel.clearAction()
            val newValue = (viewAction as Action.Sound).new
                .stripTrailingZeros()
                .toPlainString()
                .last()
            soundManager.play(newValue)
        }

        null -> {}
    }
}