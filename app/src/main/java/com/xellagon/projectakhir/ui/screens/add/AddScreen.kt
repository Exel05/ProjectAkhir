package com.xellagon.projectakhir.ui.screens.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.source.Animal
import java.io.File
import java.io.InputStream


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add",
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
                }

            )
        }
    ) {

        var animalName by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var description by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var latinName by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var kingdom by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val singlePhotoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                selectedImageUri = it
            }
        )

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                onClick = {
                    singlePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            TextField(
                value = animalName,
                placeholder = {
                    Text(text = "Animal Name")
                },
                onValueChange = {
                    animalName = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = description,
                placeholder = {
                    Text(text = "Description")
                },
                onValueChange = {
                    description = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = latinName,
                placeholder = {
                    Text(text = "Latin Name")
                },
                onValueChange = {
                    latinName = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = kingdom,
                placeholder = {
                    Text(text = "Kingdom")
                },
                onValueChange = {
                    kingdom = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {

                    val iStream: InputStream? =
                        selectedImageUri?.let { it1 ->
                            context.getContentResolver().openInputStream(
                                it1
                            )
                        }
                    val inputData: ByteArray = viewModel.getBytes(iStream!!)!!

//
                    viewModel.insert(
                        Animal(
                            idUser = Kotpref.id,
                            animalName = animalName.text,
                            animalDesc = description.text,
                            animalLatin = latinName.text,
                            animalKingdom = kingdom.text,
                            image = null
                        ),
                        selectedImageUri
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add")
            }
        }
        state.value.DisplayResult(
            onLoading = {
                CircularProgressIndicator()
            },
            onSuccess = {
                navigator.navigateUp()
            },
            onError = {

            }
        )
    }
}
