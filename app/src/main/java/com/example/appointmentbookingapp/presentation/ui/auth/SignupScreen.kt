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
fun SignupScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val uiState = viewModel.signUpState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val context = LocalContext.current
    LaunchedEffect(uiState.value) {
        when (uiState.value) {

            is AuthState.Success -> {
                Toast.makeText(context, "SignUp Successful", Toast.LENGTH_SHORT).show()
//                navController.navigate("HomeScreen")
                navController.navigate("HomeScreen") {
                    popUpTo("SignUp") { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, "SignUp Filed", Toast.LENGTH_SHORT).show()
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
        Spacer(modifier = Modifier.height(32.dp))

        WelcomeText(text = "Create New Account")

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TextInputField(
                tittle = "Full Name",
                label = "Enter Your Name",
                isPassword = false,
                value = name,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextInputField(
                tittle = "Email",
                label = "Enter Your Email",
                isPassword = false,
                value = email,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(16.dp))


            TextInputField(
                tittle = "Password",
                label = "Enter Your Password",
                isPassword = true,
                value = password,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextInputField(
                tittle = " Phone NO",
                label = "Enter Your Phone NO",
                isPassword = false,
                value = phone,
                onValueChange = { phone = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
//            ActionButton(
//                onClick = {},
//                buttonText = "Sign Up"
//            )

            if (uiState.value is AuthState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.signUp(name, email, password ,"")
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
                    Text(text = "Sign Up")
                }

            }


            Text(
                "OR",
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.titleMedium,
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
                    text = "Already have account?",
                    color = colorResource(id = R.color.gray),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Sign In",
                    color = colorResource(id = R.color.colorPrimary),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable {
//                        navController.navigate("SignIn")

                        navController.navigate("SignIn") {
                            popUpTo("SignUp") { inclusive = true }
                        }
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(navController = rememberNavController())
}