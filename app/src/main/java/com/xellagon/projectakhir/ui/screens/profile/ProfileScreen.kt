package com.xellagon.projectakhir.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.ui.screens.destinations.UpdateProfileScreenDestination
import com.xellagon.projectakhir.utils.GlobalState

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    var checked by remember {
        mutableStateOf(true)
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
                                    id = Kotpref.id,
                                    username = Kotpref.username
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
                    text = Kotpref.username,
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
                        checked = checked
                        , onCheckedChange = {
                            checked = it
                            GlobalState.isDarkMode = it
                            Kotpref.isDarkMode = it
                        }
                    )
                }
            }
        }
    }
}

data class ProfileArguments(
    val id : String,
    val username : String
)