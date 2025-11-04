package com.example.appointmentbookingapp.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.auth.components.ImageWithBorder
import com.example.appointmentbookingapp.presentation.ui.auth.components.TextInputField
import com.example.appointmentbookingapp.presentation.ui.auth.components.WelcomeText
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.UserRole

@Composable
fun SignInScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    roleSharedViewModel: UserRoleSharedViewModel = hiltViewModel()
) {
    val uiState by authViewModel.signInState.collectAsState()
    val userRole by roleSharedViewModel.userRole.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading = uiState is UiState.Loading
    val isSuccess = (uiState as? UiState.Success)?.data
    val isError = (uiState as? UiState.Error)?.message

    val context = LocalContext.current

    LaunchedEffect(isSuccess, isError) {
        when {
            isSuccess != null -> {
                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                val destination =
                    if (userRole == UserRole.DOCTOR) "DoctorHomeScreen" else "HomeScreen"
                navController.navigate(destination) {
                    popUpTo("SignIn") { inclusive = true }
                }
            }

            isError != null -> {
                Toast.makeText(context, isError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        WelcomeText(text = "Welcome")

        Spacer(modifier = Modifier.height(70.dp))

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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    "Forget Password?",
                    color = colorResource(id = R.color.colorPrimary)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.colorPrimary)
                )
            } else {
                Button(
                    onClick = { authViewModel.signIn(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.colorPrimary)
                    ),
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Sign In",
                        color = Color.White
                    )
                }
            }

            Text(
                "OR",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageWithBorder(R.drawable.google)
                ImageWithBorder(R.drawable.facebook)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Don’t have an account yet?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign Up",
                    color = colorResource(id = R.color.colorPrimary),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        val destination =
                            if (roleSharedViewModel.userRole.value == UserRole.DOCTOR) {
                                "CompleteProfileScreen"
                            } else {
                                "SignUp"
                            }
                        navController.navigate(destination) {
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