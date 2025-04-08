package com.example.appointmentbookingapp.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.ui.auth.components.ActionButton
import com.example.appointmentbookingapp.presentation.ui.auth.components.ImageWithBorder
import com.example.appointmentbookingapp.presentation.ui.auth.components.TextInputField
import com.example.appointmentbookingapp.presentation.ui.auth.components.WelcomeText
@Preview
@Composable
fun SignupScreen (){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                label = "Name",
                hint = "Enter Your Name",
                isPassword = false,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextInputField(
                label = "Email",
                hint = "Enter Your Email",
                isPassword = false,
                onValueChange = { password = it }
            )
            Spacer(modifier = Modifier.height(16.dp))


            TextInputField(
                label = "Password",
                hint = "Enter Your Password",
                isPassword = true,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextInputField(
                label = "Phone",
                hint = "Enter Your Phone NO",
                isPassword = false,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                onClick = {},
                buttonText = "Sign Up"
            )

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
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }

    }
}