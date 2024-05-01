package com.xellagon.projectakhir.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.xellagon.projectakhir.R


@Composable
@Destination
fun SplashScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xffFFA869)),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "",
            modifier = Modifier.size(300.dp)
        )
    }
}

@Preview
@Composable
fun result() {
    SplashScreen()
}
