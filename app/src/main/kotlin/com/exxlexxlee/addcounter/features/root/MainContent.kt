package com.exxlexxlee.addcounter.features.root

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.game.GameScreen
import com.exxlexxlee.addcounter.features.game.GameViewModel
import com.exxlexxlee.addcounter.features.game.models.BottomButtonsItems
import com.exxlexxlee.addcounter.features.game.models.Event
import com.exxlexxlee.addcounter.features.game.models.GameViewState
import com.exxlexxlee.addcounter.features.root.models.Action
import com.exxlexxlee.addcounter.features.root.models.MainEvent
import com.exxlexxlee.addcounter.features.root.models.ViewState
import com.exxlexxlee.addcounter.features.settings.about.AboutScreen
import com.exxlexxlee.addcounter.features.settings.donate.DonateScreen
import com.exxlexxlee.addcounter.features.settings.language.LanguageScreen
import com.exxlexxlee.addcounter.features.settings.main.SettingsScreen
import com.exxlexxlee.addcounter.features.settings.terms.TermsScreen
import com.exxlexxlee.addcounter.navigation.Routes
import com.exxlexxlee.addcounter.navigation.asRoute
import com.exxlexxlee.addcounter.navigation.isPrimaryRoute
import com.exxlexxlee.addcounter.ui.common.AnimationType
import com.exxlexxlee.addcounter.ui.common.DiceRollDialog
import com.exxlexxlee.addcounter.ui.common.HSpacer
import com.exxlexxlee.addcounter.ui.common.LocalNavController
import com.exxlexxlee.addcounter.ui.common.VSpacer
import com.exxlexxlee.addcounter.ui.common.animatedComposable
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainContent(
    mainViewModel: MainViewModel = koinViewModel(),
) {
    val mainViewState by mainViewModel.viewStates().collectAsState()
    val viewAction by mainViewModel.viewActions().collectAsState(null)

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.asRoute()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        if (isLandscape) {
            LandscapeLayout(currentRoute, navController, mainViewModel, mainViewState,)
        } else {
            PortraitLayout(currentRoute, navController, mainViewModel, mainViewState)
        }

        when (viewAction) {
            Action.Dice -> DiceRollDialog { mainViewModel.clearAction() }

            null -> {}
        }
    }
}

@Composable
private fun PortraitLayout(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel,
    viewState: ViewState,
) {
    Scaffold(
        topBar = { MainTopBar(currentRoute, navController, viewModel) },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        NavHostContent(
            navController,
            viewState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}

@Composable
private fun LandscapeLayout(
    currentRoute: Routes?,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    viewState: ViewState,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavigationRail(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                windowInsets = WindowInsets(),
            ) {
                LandscapeNavigationButtons(currentRoute, navController, mainViewModel)
            }
            NavHostContent(
                navController,
                viewState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = currentRoute?.label() ?: "",
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
            )
        },
        navigationIcon = {
            if (currentRoute?.isPrimaryRoute() == false) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            } else HSpacer(48.dp)
        },
        actions = {
            if (currentRoute?.isPrimaryRoute() == true) {
                IconButton(onClick = {
                    viewModel.obtainEvent(MainEvent.Dice)
                }) {
                    Icon(
                        painterResource(id = R.drawable.outline_person_add_24),
                        contentDescription = stringResource(R.string.add_player)
                    )
                }
                IconButton(onClick = {
                    viewModel.obtainEvent(MainEvent.MainRoute(Routes.SettingsRoute.MainRoute.route))
                    navController.navigate(Routes.SettingsRoute.MainRoute.route)
                }) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_settings_24),
                        contentDescription = stringResource(R.string.title_settings)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    )
}

@Composable
private fun LandscapeNavigationButtons(
    currentRoute: Routes?,
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    if (currentRoute?.isPrimaryRoute() == false) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                contentDescription = stringResource(R.string.back)
            )
        }
    } else {
        IconButton(onClick = {
            mainViewModel.obtainEvent(MainEvent.MainRoute(Routes.SettingsRoute.MainRoute.route))
            navController.navigate(Routes.SettingsRoute.MainRoute.route)
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_settings_24),
                contentDescription = stringResource(R.string.title_settings)
            )
        }
    }
}

