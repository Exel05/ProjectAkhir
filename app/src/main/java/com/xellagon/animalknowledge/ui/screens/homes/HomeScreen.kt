package com.xellagon.animalknowledge.ui.screens.homes

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.ui.screens.destinations.AddScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.AnimalListScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.DetailScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.FavouriteScreenDestination
import com.xellagon.animalknowledge.ui.screens.destinations.ProfileScreenDestination
import com.xellagon.animalknowledge.ui.screens.detail.DetailArguments

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val animalState = viewModel.animalState.collectAsStateWithLifecycle()
    val searchAnimal by viewModel.searchAnimal.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.connextToRealTime()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home Screen",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = { navigator.navigate(ProfileScreenDestination) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null
                                )
                            })
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Favourite") },
                            onClick = { navigator.navigate(FavouriteScreenDestination) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null
                                )
                            })
                    }
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddScreenDestination)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            SearchBar(
                query = searchAnimal,
                onQueryChange = {
                    viewModel.onSearchAnimalChange(it)
                }, onSearch = {
                    viewModel.onSearchAnimalChange(it)
                }, active = isSearching,
                onActiveChange = {
                    viewModel.onToogleSearch()
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }, placeholder = {
                    Text(text = "Search Animal")
                }
            ) {
                Column() {
                    animalState.value.DisplayResult(
                        onLoading = { },
                        onSuccess = {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2)
                            ) {
                                items(it.filter {
                                    it.animalName.contains(
                                        searchAnimal,
                                        true
                                    )
                                }) { animal ->
                                    AnimalListItem(
                                        id = animal.idUser ?: "",
                                        image = animal.image,
                                        animal = animal.animalName,
                                        onClick = {
                                            navigator.navigate(
                                                DetailScreenDestination(
                                                    navArgs = DetailArguments(
                                                        id = animal.id,
                                                        image = animal.image,
                                                        animal = animal.animalDesc,
                                                        desc = animal.animalDesc,
                                                        latin = animal.animalLatin,
                                                        kingdom = animal.animalKingdom,
                                                        idUser = animal.idUser
                                                    )
                                                )
                                            )
                                        },
                                        onDelete = {
                                            viewModel.deleteAnimal(animal.id!!)
                                            Toast.makeText(
                                                context,
                                                "Animal has been Deleted",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    )
                                }
                            }
                        },
                        onError = {
                        }
                    )
                }
            }
            animalState.value.DisplayResult(
                onLoading = { },
                onSuccess = {
                    if (it.isEmpty()) {
                        Column() {
                            Spacer(modifier = Modifier.height(52.dp))
                            Text(
                                text = "ANIMAL IS EMPTY",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                        ) {
                            items(it) { animal ->
                                AnimalListItem(
                                    id = animal.idUser ?: "",
                                    image = animal.image,
                                    animal = animal.animalName,
                                    onClick = {
                                        navigator.navigate(
                                            DetailScreenDestination(
                                                navArgs = DetailArguments(
                                                    id = animal.id,
                                                    image = animal.image,
                                                    animal = animal.animalDesc,
                                                    desc = animal.animalDesc,
                                                    latin = animal.animalLatin,
                                                    kingdom = animal.animalKingdom,
                                                    idUser = animal.idUser
                                                )
                                            )
                                        )
                                    },
                                    onDelete = {
                                        viewModel.deleteAnimal(animal.id!!)
                                        Toast.makeText(
                                            context,
                                            "Animal has been Deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    })
                            }
                        }
                    }

                },
                onError = {
                })
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.leaveRealTimeChannel()
        }
    }
}

@Composable
fun AnimalListItem(
    id: String,
    image: String,
    animal: String,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    OutlinedCard(modifier = Modifier
        .padding(8.dp)
        .height(240.dp),
        border = BorderStroke(5.dp, Color.White),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = animal,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (id == Kotpref.id) {
                IconButton(
                    onClick = {
                        onDelete()
                    },
                    modifier = Modifier.size(30.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

        }
    }
}

