package com.xellagon.projectakhir.ui.screens.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.xellagon.projectakhir.R

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AnimalListScreen() {
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "")
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
            items(10) {
                AnimalListItem()
            }
        }
    }
}

@Composable
fun AnimalListItem() {
    OutlinedCard(modifier = Modifier
        .padding(8.dp)
        .height(200.dp),
        border = BorderStroke(5.dp, Color.White)
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
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = "Nama Hewan",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
}

@Preview
@Composable
fun result5() {
    AnimalListScreen()
}