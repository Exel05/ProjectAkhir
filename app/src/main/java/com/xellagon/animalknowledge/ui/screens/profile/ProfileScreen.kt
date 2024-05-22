package com.xellagon.animalknowledge.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.ui.screens.destinations.HomeScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.LoginScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.ProfileScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.UpdateProfileScreenDestination
import com.xellagon.animalknowledge.utils.GlobalState

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator
) {
    var checked by remember {
        mutableStateOf(GlobalState.isDarkMode)
    }

    var showAllertDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(
                            UpdateProfileScreenDestination(
                                navArgs = ProfileArguments(
                                    id = Kotpref.id ?: "",
                                    username = Kotpref.username ?: ""
                                )
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = Kotpref.username ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.background
                )
            }
            Divider(color = MaterialTheme.colorScheme.background)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Settings",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DarkMode",
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                            GlobalState.isDarkMode = it
                            Kotpref.isDarkMode = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.background,
                            checkedTrackColor = MaterialTheme.colorScheme.secondary,
                            checkedBorderColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                showAllertDialog = true
                            }
                            .background(Color(0xFFAA2424))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Log Out",
                            color = Color.White
                        )
                    }
                }
                if (showAllertDialog) {
                    AlertDialog(
                        title = { Text(text = "Logout") },
                        text = {
                            Text(
                                text = "Are you sure you want to logout of this account?",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        onDismissRequest = { showAllertDialog = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    Kotpref.id = null
                                    Kotpref.username = null
                                    navigator.navigate(LoginScreenDestination) {
                                        popUpTo(ProfileScreenDestination) {
                                            inclusive = true
                                        }
                                        popUpTo(HomeScreenDestination){
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }) {
                                Text(text = "Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showAllertDialog = false
                                }) {
                                Text(text = "Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

data class ProfileArguments(
    val id: String,
    val username: String
)