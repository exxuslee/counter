package com.exxlexxlee.addcounter.features.game.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.addcounter.R

sealed class BottomButtonsItems(
    val label: @Composable () -> String,
    val icon: @Composable () -> Painter,
) {

    data object AddLevel : BottomButtonsItems(
        label = { stringResource(R.string.add_level) },
        icon = { painterResource(R.drawable.outline_trophy_24) },
    )

    data object AddBonus : BottomButtonsItems(
        label = { stringResource(R.string.add_bonus) },
        icon = { painterResource(R.drawable.outline_add_moderator_24) },
    )

    data object SubLevel : BottomButtonsItems(
        label = { stringResource(R.string.sub_level) },
        icon = { painterResource(R.drawable.outline_skull_24) },
    )

    data object SubBonus : BottomButtonsItems(
        label = { stringResource(R.string.sub_bonus) },
        icon = { painterResource(R.drawable.outline_remove_moderator_24) },
    )

}