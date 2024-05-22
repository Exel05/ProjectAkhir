package com.xellagon.animalknowledge.ui.screens.updateprofile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.animalknowledge.ui.screens.profile.ProfileArguments

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = ProfileArguments::class)
@Composable
fun UpdateProfileScreen(
    viewModel: UpdateProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val profileState = viewModel.state.collectAsStateWithLifecycle()
    val contect = LocalContext.current
    var username by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.username))
    }

    BackHandler(profileState.value.isLoading()) {

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Update",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!profileState.value.isLoading()) {
                                navigator.navigateUp()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            Button(
                enabled = !profileState.value.isLoading(),
                onClick = {
                    viewModel.updateProfile(
                        id = viewModel.navArgs.id,
                        username = username.text
                    )
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Update",
                    color = MaterialTheme.colorScheme.background
                )
            }
            profileState.value.DisplayResult(
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = {
                    Toast.makeText(contect, "Username has been changed", Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                },
                onError = {
                    Toast.makeText(contect, it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}