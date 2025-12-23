package com.exxlexxlee.addcounter.features.settings.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.settings.main.models.Action
import com.exxlexxlee.addcounter.features.settings.main.models.Event
import com.exxlexxlee.addcounter.features.settings.main.models.Event.AddPlayer
import com.exxlexxlee.addcounter.ui.common.AddPlayerDialog
import com.exxlexxlee.addcounter.ui.common.HSpacer
import com.exxlexxlee.addcounter.ui.common.LocalNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val navController = LocalNavController.current

    SettingsView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {

        Action.NewGame -> AlertDialog(
            onDismissRequest = { viewModel.clearAction() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.outline_info_24),
                        contentDescription = stringResource(id = R.string.add_player),
                    )
                    HSpacer(8.dp)
                    Text(text = stringResource(id = R.string.new_game_title))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.new_game_message))
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.obtainEvent(Event.ConfirmNewGame)
                }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.clearAction() }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        )

        Action.AddPlayer -> AddPlayerDialog(
            onDismissRequest = {
                viewModel.clearAction()
            },
            onAddPlayer = { name, selectedIcon ->
                viewModel.obtainEvent(
                    AddPlayer(name = name, icon = selectedIcon)
                )
            }
        )

        Action.PopBack -> {
            viewModel.clearAction()
            navController.popBackStack()
        }

        null -> {}
    }
}