package com.xellagon.projectakhir.ui.screens.list

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.xellagon.projectakhir.R
import com.xellagon.projectakhir.ui.screens.destinations.AddScreenDestination
import com.xellagon.projectakhir.ui.screens.destinations.DetailScreenDestination
import com.xellagon.projectakhir.ui.screens.detail.DetailArguments

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AnimalListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val animalState = viewModel.animalState.collectAsStateWithLifecycle()
    val searchAnimal by viewModel.searchAnimal.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.connextToRealTime()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "List of Animal",
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
                        navigator.navigate(AddScreenDestination)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
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
                        onLoading = {  },
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
                                                        kingdom = animal.animalKingdom
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
                onLoading = {  },
                onSuccess = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                    ) {
                        items(it) { animal ->
                            AnimalListItem(
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
                                                kingdom = animal.animalKingdom
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
fun AnimalListItem(image: String, animal: String, onClick: () -> Unit, onDelete: () -> Unit) {
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
            IconButton(
                onClick = {
                    onDelete()
                },
                modifier = Modifier.size(30.dp),
                colors = IconButtonDefaults.filledIconButtonColors(Color.Red)
            )
            {
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

//@Preview
//@Composable
//fun result5() {
//    AnimalListScreen()
//}