package com.xellagon.animalknowledge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.ui.screens.NavGraphs
import com.xellagon.animalknowledge.ui.screens.destinations.HomeScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.LoginScreenDestination
import com.xellagon.animalknowledge.ui.theme.ProjectAkhirTheme
import com.xellagon.animalknowledge.utils.GlobalState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ProjectAkhirTheme(
                darkTheme = GlobalState.isDarkMode
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    startRoute = if (Kotpref.username.isNullOrEmpty()) LoginScreenDestination else HomeScreenDestination
                )
            }
        }
    }
}