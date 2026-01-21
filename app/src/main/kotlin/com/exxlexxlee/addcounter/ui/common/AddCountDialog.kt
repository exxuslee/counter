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
import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.model.Operator
import java.math.BigDecimal
import kotlin.random.Random


@Composable
fun AddCountDialog(
    onDismissRequest: () -> Unit,
    onAdd: (Count) -> Unit,
) {
    var selectedIcon by remember { mutableIntStateOf(Random.nextInt(Icons.all.size)) }
    var selectedColor by remember { mutableIntStateOf(Random.nextInt(Icons.colors.size)) }
    var name by remember { mutableStateOf("") }
    var start by remember { mutableStateOf("0") }
    var increment by remember { mutableStateOf("1") }
    var selectedOperator by remember { mutableStateOf(Operator.ADD) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.round_add_circle_outline_24),
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

                var expandedIcon by remember { mutableStateOf(false) }
                var expandedColor by remember { mutableStateOf(false) }
                var expandedOperator by remember { mutableStateOf(false) }
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
                                        onClick = { expandedIcon = true }
                                    ),
                                painter = painterResource(id = Icons.icon(selectedIcon)),
                                contentDescription = stringResource(R.string.select_icon)
                            )
                            DropdownMenu(
                                expanded = expandedIcon,
                                onDismissRequest = { expandedIcon = false }
                            ) {
                                Icons.all.forEachIndexed { index, icon ->
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
                                            expandedIcon = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    trailingIcon = {
                        Box {
                            Image(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable(
                                        onClick = { expandedColor = true }
                                    ),
                                painter = painterResource(id = Icons.circle(selectedColor)),
                                contentDescription = stringResource(R.string.select_icon)
                            )
                            DropdownMenu(
                                expanded = expandedColor,
                                onDismissRequest = { expandedColor = false }
                            ) {
                                Icons.colors.forEachIndexed { index, colorCircle ->
                                    DropdownMenuItem(
                                        text = {
                                            Image(
                                                painter = painterResource(id = colorCircle),
                                                modifier = Modifier.size(36.dp),
                                                contentDescription = stringResource(R.string.select_icon)
                                            )
                                        },
                                        onClick = {
                                            selectedColor = index
                                            expandedColor = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    placeholder = { Text(stringResource(R.string.input_name)) },
                    singleLine = true,
                )
                VSpacer(16.dp)

                OutlinedTextField(
                    value = start,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("-?\\d*\\.?\\d*"))) {
                            start = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.start)) },
                    placeholder = { Text("0") },
                    singleLine = true,
                )
                VSpacer(16.dp)

                OutlinedTextField(
                    value = increment,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("-?\\d*\\.?\\d*"))) {
                            increment = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.increment)) },
                    placeholder = { Text("1") },
                    singleLine = true,
                    leadingIcon = {
                        Box {
                            Image(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable(
                                        onClick = { expandedOperator = true }
                                    ),
                                painter = painterResource(id = selectedOperator.res),
                                contentDescription = stringResource(R.string.select_icon)
                            )
                            DropdownMenu(
                                expanded = expandedOperator,
                                onDismissRequest = { expandedOperator = false }
                            ) {
                                Operator.entries.forEach { operator ->
                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Image(
                                                painter = painterResource(id = operator.res),
                                                modifier = Modifier.size(36.dp),
                                                contentDescription = stringResource(R.string.select_icon)
                                            )
                                        },
                                        text = {
                                            val text = when (operator) {
                                                Operator.ADD -> stringResource(R.string.add)
                                                Operator.SUBTRACT -> stringResource(R.string.sub)
                                                Operator.MULTIPLY -> stringResource(R.string.multiple)
                                                Operator.DIVIDE -> stringResource(R.string.divide)
                                            }
                                            Text(text)
                                        },
                                        onClick = {
                                            selectedOperator = operator
                                            expandedOperator = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                )
            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    val startValue = try {
                        if (start.isEmpty()) BigDecimal.ZERO else BigDecimal(start)
                    } catch (e: NumberFormatException) {
                        BigDecimal.ZERO
                    }
                    val incrementValue = try {
                        if (increment.isEmpty()) BigDecimal.ONE else BigDecimal(increment)
                    } catch (e: NumberFormatException) {
                        BigDecimal.ONE
                    }
                    onAdd.invoke(
                        Count(
                            name = name,
                            start = startValue,
                            current = startValue,
                            increment = incrementValue,
                            operator = selectedOperator,
                            icon = selectedIcon,
                            color = selectedColor,
                        )
                    )
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