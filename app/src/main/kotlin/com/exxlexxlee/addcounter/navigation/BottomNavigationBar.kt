package com.exxlexxlee.addcounter.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.features.game.models.BottomButtonsItems
import com.exxlexxlee.addcounter.features.game.models.Event

@Composable
fun BottomNavigationBar(
    eventHandler: (Event) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.height(56.dp),
        windowInsets = WindowInsets(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        listOf(
            BottomButtonsItems.AddLevel,
            BottomButtonsItems.SubLevel,
            BottomButtonsItems.AddBonus,
            BottomButtonsItems.SubBonus,
        ).forEach { dest ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    eventHandler.invoke(
                        when (dest) {
                            BottomButtonsItems.AddLevel -> Event.AddLevel
                            BottomButtonsItems.SubLevel -> Event.SubLevel
                            BottomButtonsItems.AddBonus -> Event.AddBonus
                            BottomButtonsItems.SubBonus -> Event.SubBonus
                        }
                    )
                },
                icon = {
                    Icon(
                        painter = dest.icon(),
                        contentDescription = dest.label(),
                        modifier = Modifier.size(30.dp)
                    )
                },
            )
        }
    }
}