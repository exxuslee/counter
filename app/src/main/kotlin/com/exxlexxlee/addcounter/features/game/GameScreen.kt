package com.exxlexxlee.addcounter.features.game

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.addcounter.features.game.models.Action
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.ui.common.AddCountDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    GameView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        is Action.ShowSelectPlayerMessage -> {
            Toast.makeText(
                LocalContext.current,
                stringResource((viewAction as Action.ShowSelectPlayerMessage).messageResId),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.clearAction()
        }

        Action.AddPlayer -> {
            AddCountDialog(
                onDismissRequest = {
                    viewModel.clearAction()
                },
                onAdd = { count ->
                    viewModel.obtainEvent(Event.AddCount(count))
                }
            )
        }

        null -> {}
    }
}