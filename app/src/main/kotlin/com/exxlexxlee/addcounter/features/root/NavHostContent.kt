package com.exxlexxlee.addcounter.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.exxlexxlee.addcounter.features.game.GameScreen
import com.exxlexxlee.addcounter.features.game.GameViewModel
import com.exxlexxlee.addcounter.features.root.models.ViewState
import com.exxlexxlee.addcounter.features.settings.about.AboutScreen
import com.exxlexxlee.addcounter.features.settings.donate.DonateScreen
import com.exxlexxlee.addcounter.features.settings.language.LanguageScreen
import com.exxlexxlee.addcounter.features.settings.main.SettingsScreen
import com.exxlexxlee.addcounter.features.settings.terms.TermsScreen
import com.exxlexxlee.addcounter.navigation.Routes
import com.exxlexxlee.addcounter.ui.common.AnimationType
import com.exxlexxlee.addcounter.ui.common.animatedComposable

@Composable
fun NavHostContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    gameViewModel: GameViewModel,
    viewState: ViewState,
) {
    NavHost(
        navController = navController,
        startDestination = viewState.initialRoute,
        modifier = modifier
    ) {
        animatedComposable(Routes.GameRoute.route) { GameScreen(gameViewModel) }
        animatedComposable(Routes.SettingsRoute.MainRoute.route) { SettingsScreen() }
        animatedComposable(
            Routes.SettingsRoute.ThermsRoute.route,
            animationType = AnimationType.FADE
        ) { TermsScreen() }
        animatedComposable(
            Routes.SettingsRoute.LanguageRoute.route,
            animationType = AnimationType.FADE
        ) { LanguageScreen() }
        animatedComposable(
            Routes.SettingsRoute.AboutRoute.route,
            animationType = AnimationType.FADE
        ) { AboutScreen() }
        animatedComposable(
            Routes.SettingsRoute.DonateRoute.route,
            animationType = AnimationType.FADE
        ) { DonateScreen() }
    }
}