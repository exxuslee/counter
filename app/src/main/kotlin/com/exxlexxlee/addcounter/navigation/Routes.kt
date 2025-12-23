package com.exxlexxlee.addcounter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.addcounter.R

sealed class Routes(
    val route: String,
    val label: @Composable () -> String,
    val icon: @Composable () -> Painter,
) {

    data object GameRoute : Routes(
        "game",
        label = { stringResource(R.string.counter) },
        icon = { painterResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24) },
    )

    sealed class SettingsRoute(subRoute: String, label: @Composable () -> String) :
        Routes(
            "settings/$subRoute",
            label = label,
            icon = { painterResource(R.drawable.ic_baseline_settings_24) },
        ) {
        data object MainRoute :
            SettingsRoute("main", label = { stringResource(R.string.title_settings) })

        data object ThermsRoute :
            SettingsRoute("terms", label = { stringResource(R.string.terms_title) })

        data object LanguageRoute :
            SettingsRoute("language", label = { stringResource(R.string.language) })


        data object DonateRoute :
            SettingsRoute("donate", label = { stringResource(R.string.donate) })

        data object AboutRoute :
            SettingsRoute("about", label = { stringResource(R.string.about, R.string.app_name) })

    }

}
