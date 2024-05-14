package com.xellagon.projectakhir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.ui.screens.NavGraphs
import com.xellagon.projectakhir.ui.screens.destinations.HomeScreenDestination
import com.xellagon.projectakhir.ui.screens.destinations.LoginScreenDestination
import com.xellagon.projectakhir.ui.screens.destinations.RegisterScreenDestination
import com.xellagon.projectakhir.ui.theme.ProjectAkhirTheme
import com.xellagon.projectakhir.utils.GlobalState
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