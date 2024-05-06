package com.xellagon.projectakhir.ui.screens.homes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.R
import com.xellagon.projectakhir.ui.screens.destinations.AnimalListScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Dashboard",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xFFFFB580)),
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = { /*TODO*/ },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null
                                )
                            })
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Favourite") },
                            onClick = { /*TODO*/ },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null
                                )
                            })
                    }
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(it)
                .background(Color(0xffFFA869))
        ) {

//            SearchBar(
//                query = ,
//                onQueryChange = ,
//                onSearch = ,
//                active = ,
//                onActiveChange = ) {
//
//            }

            items(10) {
                HomeItem(onClick = {
                    navigator.navigate(AnimalListScreenDestination)
                })
            }
        }
    }
}

@Composable
fun HomeItem(onClick : ()-> Unit) {
    OutlinedCard(
        modifier = Modifier
        .padding(16.dp)
        .height(230.dp),
        border = BorderStroke(5.dp, Color.White),
        onClick = {
            onClick()
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffE5E4E2)),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(painter = painterResource(id = R.drawable.carnivore),
                contentDescription = "",
                modifier = Modifier.size(120.dp))
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Karnivora",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
}


//@Preview
//@Composable
//fun result2() {
//    HomeScreen()
//}