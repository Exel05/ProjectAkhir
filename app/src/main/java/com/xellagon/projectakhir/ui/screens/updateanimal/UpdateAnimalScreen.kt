package com.xellagon.projectakhir.ui.screens.updateanimal

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.ui.screens.detail.DetailArguments

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailArguments::class)
@Composable
fun UpdateAnimalScreen(
    viewModel: UpdateAnimalViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,

    ) {

    val context = LocalContext.current
    val animalState = viewModel.animalState.collectAsStateWithLifecycle()

    var animalName by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.animal!!))
    }

    var description by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.desc!!))
    }

    var latinName by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.latin!!))
    }

    var kingdom by remember {
        mutableStateOf(TextFieldValue(viewModel.navArgs.kingdom!!))
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
                            navigator.navigateUp()
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            }
            TextField(
                value = animalName,
                onValueChange = {
                    animalName = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = latinName,
                onValueChange = {
                    latinName = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = kingdom,
                onValueChange = {
                    kingdom = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    viewModel.updateAnimal(
                        id = viewModel.navArgs.id!!,
                        image = "",
                        animal = animalName.text,
                        desc = description.text,
                        latin = latinName.text,
                        kingdom = kingdom.text
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Update",
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
        animalState.value.DisplayResult(
            onLoading = {
                        CircularProgressIndicator()
            },
            onSuccess = {
                navigator.navigateUp()
            },
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        )
    }
}