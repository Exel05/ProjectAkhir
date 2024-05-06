package com.xellagon.projectakhir.ui.screens.list

import android.util.Log
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.R
import com.xellagon.projectakhir.ui.screens.destinations.AddScreenDestination
import com.xellagon.projectakhir.ui.screens.destinations.DetailScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AnimalListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val listState by viewModel.listState.collectAsStateWithLifecycle()
    val listAnimal = remember {
        viewModel.listAnimal
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Jenis Hewan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xFFFFB580)),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(AddScreenDestination)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }

            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .background(Color(0xffFFA869)))
        {
            listState.DisplayResult(
                onLoading = { /*TODO*/ },
                onSuccess = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier

                    ) {
                        items(listAnimal) { animal ->
                            AnimalListItem(
                                animal = animal.animalName,
                                onClick = {
                                navigator.navigate(DetailScreenDestination)
                                          },
                                onDelete = {
                                viewModel.deleteAnimal(animal.id!!)
                                Log.d("skdjfmnowr", animal.id!!.toString())
                            } )
                        }
                    }
                },
                onError = {

                })
        }

    }
}

//data class DetailArguments(
//    val id : Int?,
//    val image : String?,
//    val animal : String?,
//    val desc : String?,
//    val latin : String?,
//    val kingdom : String?
//)

@Composable
fun AnimalListItem( animal : String, onClick : ()-> Unit, onDelete : ()-> Unit) {
    OutlinedCard(modifier = Modifier
        .padding(8.dp)
        .height(240.dp),
        border = BorderStroke(5.dp, Color.White),
        onClick = {
            onClick()
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffE5E4E2)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = animal,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            IconButton(
                onClick = {
                          onDelete()
            },
                modifier = Modifier.size(30.dp),
                colors = IconButtonDefaults.filledIconButtonColors(Color.Red))
            {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp))
            }
        }
    }
}

//@Preview
//@Composable
//fun result5() {
//    AnimalListScreen()
//}