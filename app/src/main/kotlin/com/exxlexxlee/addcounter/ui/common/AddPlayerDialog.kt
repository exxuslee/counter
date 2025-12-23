package com.exxlexxlee.addcounter.ui.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R

@Composable
fun AddPlayerDialog(
    onDismissRequest: () -> Unit,
    onAddPlayer: (String, Int) -> Unit,
) {
    var selectedIcon by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.outline_person_add_24),
                    contentDescription = stringResource(id = R.string.add_player),
                )
                HSpacer(8.dp)
                Text(text = stringResource(id = R.string.add_player))
            }
        },
        text = {
            Column {
                Text(
                    text = stringResource(id = R.string.add_player_message),
                )
                VSpacer(16.dp)

                var expanded by remember { mutableStateOf(false) }
                val localContext = LocalContext.current
                val text = stringResource(R.string.max_lenght_name)
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        if (it.length < 12) name = it
                        else Toast.makeText(localContext, text, Toast.LENGTH_SHORT).show()
                    },
                    label = { Text(stringResource(R.string.name)) },
                    leadingIcon = {
                        Box {
                            Image(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable(
                                        onClick = { expanded = true }
                                    ),
                                painter = painterResource(id = Icons.icon(selectedIcon)),
                                contentDescription = stringResource(R.string.select_icon)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                Icons.start.forEachIndexed { index, icon ->
                                    DropdownMenuItem(
                                        text = {
                                            Image(
                                                painter = painterResource(id = icon),
                                                modifier = Modifier.size(36.dp),
                                                contentDescription = stringResource(R.string.select_icon)
                                            )
                                        },
                                        onClick = {
                                            selectedIcon = index
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    placeholder = { Text(stringResource(R.string.input_name)) },
                    singleLine = true,
                )
            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddPlayer.invoke(name, selectedIcon)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.add),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    )
}