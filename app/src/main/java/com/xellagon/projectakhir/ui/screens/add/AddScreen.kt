package com.xellagon.projectakhir.ui.screens.add

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
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.source.Animal

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val addState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xFFFFB580)),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
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

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xffFFA869)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = null,
                    contentDescription = ""
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
                    viewModel.insert(
                    Animal(
                        idUser = Kotpref.id,
                        animalName = animalName.text,
                        animalDesc = description.text,
                        animalLatin = latinName.text,
                        animalKingdom = kingdom.text
                    )
                ) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add")
            }
        }
        addState.DisplayResult(
            onLoading = { /*TODO*/ },
            onSuccess = {

            },
        onError = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.d("kjsdnvskdjv", it)
        })
    }
}
