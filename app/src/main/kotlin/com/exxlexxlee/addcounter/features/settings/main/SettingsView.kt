package com.exxlexxlee.addcounter.features.settings.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.settings.main.models.Event
import com.exxlexxlee.addcounter.features.settings.main.models.ViewState
import com.exxlexxlee.addcounter.navigation.Routes
import com.exxlexxlee.addcounter.ui.common.CellUniversalSection
import com.exxlexxlee.addcounter.ui.common.DraggableCardSimple
import com.exxlexxlee.addcounter.ui.common.HsRow
import com.exxlexxlee.addcounter.ui.common.Icons
import com.exxlexxlee.addcounter.ui.common.LocalNavController
import com.exxlexxlee.addcounter.ui.common.RowUniversal
import com.exxlexxlee.addcounter.ui.common.VSpacer
import com.exxlexxlee.addcounter.ui.theme.AppTheme


@Composable
fun SettingsView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.verticalScroll(scrollState),
    ) {

        VSpacer(24.dp)
        CellUniversalSection {
            Text(
                text = stringResource(R.string.counter),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        CellUniversalSection(
            listOf(
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_fiber_new_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.new_game),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            eventHandler.invoke(Event.DialogNewGame)
                        },
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.round_add_circle_outline_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.add_player),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            eventHandler.invoke(Event.DialogAddPlayer)
                        },
                        arrowRight = true,
                    )
                },

                )
        )

        VSpacer(24.dp)
        CellUniversalSection {
            Text(
                text = stringResource(R.string.active_players),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        CellUniversalSection(
            viewState.counts.mapIndexed { index, player ->
                {
                    RowUniversal(
                        horizontalArrangement = Arrangement.End,
                        onClick = {
                            eventHandler.invoke(Event.DeletePlayer(player.id))
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_remove_circle_outline_24),
                            contentDescription = stringResource(R.string.remove_player),
                            modifier = Modifier.padding(horizontal = 24.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    DraggableCardSimple(
                        isRevealed = viewState.revealedId == index,
                        cardOffset = 64f,
                        onReveal = {
                            eventHandler.invoke(Event.Reveal(index))
                        },
                        onCancel = {
                            eventHandler.invoke(Event.Reveal(-1))
                        },
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 1.dp)
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                        ) {
                            HsRow(
                                iconContent = {
                                    Image(
                                        painterResource(Icons.icon(player.icon)),
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = null,
                                    )
                                },
                                titleContent = {
                                    Text(
                                        player.name,
                                        modifier = Modifier.padding(horizontal = 12.dp),
                                        color = MaterialTheme.colorScheme.secondary,
                                    )
                                },
                                onClick = {
                                    eventHandler.invoke(Event.ActivatePlayer(player))
                                },
                                valueContent = {
                                    if (player.active) {
                                        Icon(
                                            painter = painterResource(R.drawable.outline_check_24),
                                            contentDescription = "",
                                            modifier = Modifier.padding(horizontal = 12.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                arrowRight = false,
                            )
                        }

                    }

                }
            }
        )

        VSpacer(24.dp)
        CellUniversalSection {
            Text(
                text = stringResource(R.string.app),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
        CellUniversalSection(
            listOf(
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_routine_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.label_dark_mode),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        arrowRight = false,
                    ) {
                        Switch(
                            checked = viewState.isDark,
                            onCheckedChange = { eventHandler.invoke(Event.IsDark(!viewState.isDark)) },
                            colors = SwitchDefaults.colors()
                        )
                    }
                },
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_language_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.language),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.SettingsRoute.LanguageRoute.route)
                        },
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_contract_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.terms_of_use),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.SettingsRoute.ThermsRoute.route)
                        },
                        arrowRight = true,
                    ) {
                        if (!viewState.isTermsOfUseRead) Icon(
                            painter = painterResource(R.drawable.rounded_warning_24),
                            contentDescription = "",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_volunteer_activism_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.donate),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.SettingsRoute.DonateRoute.route)
                        },
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_mobile_info_24),
                        titleContent = {
                            Text(
                                stringResource(R.string.about_app),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.SettingsRoute.AboutRoute.route)
                        },
                        arrowRight = true,
                    )
                },
            )
        )
        VSpacer(32.dp)
    }
}

@Preview
@Composable
fun ProfileView_Preview() {
    AppTheme {
        SettingsView(
            viewState = ViewState(
                isTermsOfUseRead = false,
            ),
            eventHandler = {},
        )
    }
}