package com.example.appointmentbookingapp.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.ui.auth.components.ImageWithBorder
import com.example.appointmentbookingapp.presentation.ui.auth.components.TextInputField
import com.example.appointmentbookingapp.presentation.ui.auth.components.WelcomeText
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navController: NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val uiState = viewModel.signInState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is AuthState.Success -> {
                Toast.makeText(context, "SignIn Successful", Toast.LENGTH_SHORT).show()
//                navController.navigate("HomeScreen")
                navController.navigate("HomeScreen") {
                    popUpTo("SignIn") { inclusive = true }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(context, "SignIn Filed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        WelcomeText(text = "Welcome")

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TextInputField(
                tittle = "Email",
                label = "Enter Your Email",
                value = email,
                isPassword = false,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInputField(
                tittle = "Password",
                label = "Enter Your Password",
                value = password,
                isPassword = true,
                onValueChange = { password = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End,

                ) {
                Text(
                    "Forget Password?",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.value is AuthState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.signIn(email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.colorPrimary)
                    ),
                    enabled = email.isNotEmpty() && password.isNotEmpty() && (uiState.value == AuthState.Initial || uiState.value is AuthState.Error),

                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Sign In")
                }

            }

            Text(
                "OR",
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                ImageWithBorder(R.drawable.google)
                ImageWithBorder(R.drawable.facebook)
            }
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don’t have an account yet?",
                    color = colorResource(id = R.color.gray),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Sign Up",
                    color = colorResource(id = R.color.colorPrimary),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable {
//                        navController.navigate("SignUp")
                        navController.navigate("SignUp") {
                            popUpTo("SignIn") { inclusive = true }
                        }
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}