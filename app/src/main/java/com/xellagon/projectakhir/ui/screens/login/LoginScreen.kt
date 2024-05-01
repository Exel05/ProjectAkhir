package com.xellagon.projectakhir.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.projectakhir.R
import com.xellagon.projectakhir.ui.screens.destinations.HomeScreenDestination
import com.xellagon.projectakhir.ui.screens.destinations.RegisterScreenDestination

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val loginState = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var email by remember {
            mutableStateOf(TextFieldValue(""))
        }

        var password by remember {
            mutableStateOf(TextFieldValue(""))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFFA869))
                .height(230.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "",
                    modifier = Modifier.size(250.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(600.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Welcome Back!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC05A11)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row() {
                    Text(
                        text = "Email",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(270.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    modifier = Modifier.width(320.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    Text(
                        text = "Password",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(240.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.width(320.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.login(email.text, password.text) },
                    modifier = Modifier
                        .width(320.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFC05A11))
                ) {
                    Text(
                        text = "Sign In",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(180.dp))
                Row{
                   Text(
                       text = "Not have an account?",
                       fontSize = 16.sp
                   )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Sign Up",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC05A11),
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navigator.navigate(RegisterScreenDestination)
                        }
                    )
                }
            }

            loginState.value.DisplayResult(
                onLoading = { /*TODO*/ },
                onSuccess ={ navigator.navigate(HomeScreenDestination)},
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

        }

    }
}

//@Preview
//@Composable
//fun result3() {
//    LoginScreen()
//}