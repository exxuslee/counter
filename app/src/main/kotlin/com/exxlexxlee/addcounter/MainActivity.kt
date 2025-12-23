package com.exxlexxlee.addcounter

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.exxlexxlee.domain.usecases.ThemeController
import com.exxlexxlee.addcounter.features.root.MainContent
import com.exxlexxlee.addcounter.ui.common.rememberDoubleBackPressHandler
import com.exxlexxlee.addcounter.ui.theme.AppTheme
import com.hwasfy.localize.util.LocaleHelper
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            super.attachBaseContext(LocaleHelper.wrapContext(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeController: ThemeController = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val doubleBackPressHandler = rememberDoubleBackPressHandler(this@MainActivity)

            BackHandler {
                doubleBackPressHandler.handleBackPress()
            }

            AppTheme(isDark) {
                MainContent()
            }
        }
    }

}
