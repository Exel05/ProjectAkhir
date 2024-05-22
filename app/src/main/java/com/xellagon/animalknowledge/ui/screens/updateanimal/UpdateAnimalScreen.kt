package com.xellagon.animalknowledge.ui.screens.updateanimal

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.animalknowledge.source.Animal
import com.xellagon.animalknowledge.ui.screens.detail.DetailArguments

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

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
        }
    )


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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                onClick = {
                    singlePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if (selectedImageUri == null) {
                            viewModel.navArgs.image
                        } else {
                            selectedImageUri
                        })
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .build()
                    , contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
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
                        animal = Animal(
                            animalName = animalName.text,
                            animalDesc = description.text,
                            animalLatin = latinName.text,
                            animalKingdom = kingdom.text,
                            image = viewModel.navArgs.image.toString(),
                            id = viewModel.navArgs.id!!
                        ),
                        uri = selectedImageUri
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
        }
        animalState.value.DisplayResult(
            onLoading = {
                CircularProgressIndicator()
            },
            onSuccess = {
                Toast.makeText(context, "Animal has been Updated", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            },
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        )
    }
}