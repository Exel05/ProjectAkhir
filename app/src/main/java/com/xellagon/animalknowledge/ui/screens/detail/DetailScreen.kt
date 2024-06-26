package com.xellagon.animalknowledge.ui.screens.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.animalknowledge.R
import com.xellagon.animalknowledge.data.datasource.local.entity.Favourite
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.ui.screens.destinations.UpdateAnimalScreenDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailArguments::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val animalState = viewModel.animalState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarScope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(true) {
        if (!viewModel.navArgs.isFromFav) {
            viewModel.connectToRealTime(viewModel.navArgs.id!!)
        } else {
            viewModel.getFavData()
            snackBarScope.launch {
                snackbarHostState.showSnackbar(message = "This is old data from favourite")
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Jenis Hewan",
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
                    if (viewModel.navArgs.idUser == Kotpref.id) {
                        IconButton(onClick = {
                            navigator.navigate(
                                UpdateAnimalScreenDestination(
                                    navArgs = DetailArguments(
                                        id = animalState.value.getSuccessData().id,
                                        image = animalState.value.getSuccessData().image,
                                        animal = animalState.value.getSuccessData().animalName,
                                        desc = animalState.value.getSuccessData().animalDesc,
                                        latin = animalState.value.getSuccessData().animalLatin,
                                        kingdom = animalState.value.getSuccessData().animalKingdom,
                                        idUser = animalState.value.getSuccessData().idUser
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

                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val fav = Favourite(
                    id = animalState.value.getSuccessData().id!!,
                    image = animalState.value.getSuccessData().image,
                    animal = animalState.value.getSuccessData().animalName,
                    desc = animalState.value.getSuccessData().animalDesc,
                    latin = animalState.value.getSuccessData().animalLatin,
                    kingdom = animalState.value.getSuccessData().animalKingdom,
                    idUser = animalState.value.getSuccessData().idUser ?: ""
                )
                viewModel.insertFav(fav)
                Toast.makeText(context, "Added to Favourite", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = ""
                )
            }
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            animalState.value.DisplayResult(
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AsyncImage(
                            model = it.image,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                            fallback = painterResource(id = R.drawable.carnivore),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = it.animalName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .background(MaterialTheme.colorScheme.secondary),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Nama Latin",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = it.animalLatin,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .background(MaterialTheme.colorScheme.secondary),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Kingdom",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = it.animalKingdom,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "About ${it.animalName}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = MaterialTheme.colorScheme.background
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.animalDesc,
                                fontSize = 21.sp,
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                },
                onError = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            if (!viewModel.navArgs.isFromFav) {
                viewModel.leaveRealTimeChannel()
            }
        }
    }
}

data class DetailArguments(
    val id: Int?,
    val idUser: String?,
    val image: String?,
    val animal: String?,
    val desc: String?,
    val latin: String?,
    val kingdom: String?,
    val isFromFav: Boolean = false
)
