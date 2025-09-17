package com.example.appointmentbookingapp.presentation.ui.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorExtras
import com.example.appointmentbookingapp.presentation.ui.auth.AuthViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.UserRole

val docCategories = listOf(
    "Cardiologist",
    "Dermatologist",
    "Pediatrician",
    "Neurologist",
    "Orthopedist",
    "Psychiatrist"
)
val languagesList =
    listOf("English", "Spanish", "French", "German", "Urdu", "Arabic", "Hindi", "Russian")
val genders = listOf("Male", "Female", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    roleSharedViewModel: UserRoleSharedViewModel = hiltViewModel()
) {
//    val userRole by roleSharedViewModel.userRole.collectAsState()

val userRole = UserRole.DOCTOR /////////// Temporary for Testing only

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    // Doctor info states
    var aboutDoctor by remember { mutableStateOf("") }
    var docCategory by remember { mutableStateOf("") }
    var experienceYears by remember { mutableStateOf("") }
    var consultationFee by remember { mutableStateOf("") }
    var languagesSpoken by remember { mutableStateOf(mutableStateListOf<String>()) }
    var gender by remember { mutableStateOf("") }

    var currentPage by remember { mutableIntStateOf(0) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedUri = uri
            Log.d("CompleteProfile",uri.toString())
        }
    )

    roleSharedViewModel.setUserRole(UserRole.DOCTOR)
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // --- Progress Indicator ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicator(text = "Personal", isActive = currentPage == 0)
                Spacer(Modifier.width(8.dp))
                StepIndicator(text = "Additional", isActive = currentPage == 1)
                Spacer(Modifier.width(8.dp))
                StepIndicator(text = "Review", isActive = currentPage == 2)
            }

            // --- Screen Content ---
            when (currentPage) {
                0 -> PersonalInformationScreen(
                    name = name,
                    onNameChange = { name = it },
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { phoneNumber = it },
                    profileUri = selectedUri,
                    onEditProfileClick = {
                        imagePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )

                1 -> AdditionalInformationScreen(
                    aboutDoctor = aboutDoctor,
                    onAboutDoctorChange = { aboutDoctor = it },
                    docCategory = docCategory,
                    onDocCategoryChange = { docCategory = it },
                    experienceYears = experienceYears,
                    onExperienceYearsChange = { experienceYears = it },
                    consultationFee = consultationFee,
                    onConsultationFeeChange = { consultationFee = it },
                    languagesSpoken = languagesSpoken,
                    onAddLanguage = { languagesSpoken.add(it) },
                    onRemoveLanguage = { languagesSpoken.remove(it) },
                    gender = gender,
                    onGenderChange = { gender = it }
                )

                2 -> ReviewApplicationScreen(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    aboutDoctor = aboutDoctor,
                    docCategory = docCategory,
                    experienceYears = experienceYears,
                    consultationFee = consultationFee,
                    languagesSpoken = languagesSpoken,
                    gender = gender,
                    profileUri = selectedUri
                )
            }

            Spacer(Modifier.weight(1f))

            BackHandler(enabled = currentPage > 0) {
                currentPage--
            }
            // --- Navigation Buttons ---
            Button(
                onClick = {
                    when (currentPage) {
                        0 -> currentPage++
                        1 -> currentPage++
                        2 -> {
                            // handling of submit button here

                            val doctorExtras = DoctorExtras(
                                aboutDoctor = aboutDoctor,
                                docCategory = docCategory,
                                experienceYears = experienceYears.toInt(),
                                consultationFee = consultationFee,
                                languagesSpoken = languagesSpoken,
                                gender = gender
                            )
                            val uriString = selectedUri?.toString()?:""

                            viewModel.signUp(
                                name, email, password, uriString, userRole,
                                doctorExtras = doctorExtras
                            )
//                            val restoredUri = uriString?.let { Uri.parse(it) }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.colorPrimary),
                    contentColor = Color.White
                ),
                enabled = when (currentPage) {
                    0 -> name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && phoneNumber.isNotBlank()
                    1 -> aboutDoctor.isNotBlank() && docCategory.isNotBlank() && experienceYears.isNotBlank() && consultationFee.isNotBlank()
                    2 -> true
                    else -> false
                }
            ) {
                Text(
                    text = when (currentPage) {
                        0 -> "Continue"
                        1 -> "Continue"
                        2 -> "Submit"
                        else -> ""
                    }
                )
            }
        }
    }
}

// Custom step indicator composable
@Composable
fun StepIndicator(text: String, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (isActive) colorResource(R.color.colorPrimary) else Color.LightGray,
                    shape = RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (when (text) {
                    "Personal" -> 1
                    "Additional" -> 2
                    "Review" -> 3
                    else -> 0
                }).toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isActive) colorResource(R.color.colorPrimary) else Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProfileImage(
    profileUri: Uri?,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        if (profileUri != null) {
            AsyncImage(
                model = profileUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(1.dp, colorResource(R.color.colorPrimary), CircleShape),
                contentScale = ContentScale.Crop,
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.demo_user),
                contentDescription = "Default profile",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, colorResource(R.color.colorPrimary), CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Profile",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)// this padding to move whole icon
                .size(24.dp)
                .background(
                    colorResource(R.color.colorPrimary),
                    shape = CircleShape
                )
                .padding(4.dp)// this padding to move inside icon (pencil)
                .clickable {
                    onEditClick()
                },
            tint = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformationScreen(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    profileUri: Uri?,
    onEditProfileClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Create Your Account",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            "Let us get to know you a bit better by sharing your basic info.",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth(0.9f),
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))

        ProfileImage(profileUri = profileUri, onEditProfileClick)

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (name.isNotBlank()) {
                    Icon(Icons.Default.Check, contentDescription = "Valid")
                }
            },
            singleLine = true
        )
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (email.isNotBlank()) {
                    Icon(Icons.Default.Check, contentDescription = "Valid")
                }
            },
            singleLine = true
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInformationScreen(
    aboutDoctor: String,
    onAboutDoctorChange: (String) -> Unit,
    docCategory: String,
    onDocCategoryChange: (String) -> Unit,
    experienceYears: String,
    onExperienceYearsChange: (String) -> Unit,
    consultationFee: String,
    onConsultationFeeChange: (String) -> Unit,
    languagesSpoken: List<String>,
    onAddLanguage: (String) -> Unit,
    onRemoveLanguage: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
) {
    var isDocCategoryExpanded by remember { mutableStateOf(false) }
    var isGenderExpanded by remember { mutableStateOf(false) }
    var isLanguagesExpanded by remember { mutableStateOf(false) }
    var languagesFilterText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "In order to match you with the right opportunities we need some additional information first.",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth(0.9f),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = aboutDoctor,
            onValueChange = onAboutDoctorChange,
            label = { Text("About Doctor") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            placeholder = { Text("A multi-line description of your experience...") }
        )

        // Specialization Dropdown
        ExposedDropdownMenuBox(
            expanded = isDocCategoryExpanded,
            onExpandedChange = { isDocCategoryExpanded = !isDocCategoryExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth(),
                readOnly = true,
                value = docCategory,
                onValueChange = {},
                label = { Text("Specialization") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDocCategoryExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = isDocCategoryExpanded,
                onDismissRequest = { isDocCategoryExpanded = false }
            ) {
                docCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            onDocCategoryChange(category)
                            isDocCategoryExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = experienceYears,
            onValueChange = onExperienceYearsChange,
            label = { Text("Years of Experience") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = consultationFee,
            onValueChange = onConsultationFeeChange,
            label = { Text("Consultation Fee") },
            prefix = { Text("$") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Languages Spoken with multi-select chips
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = isLanguagesExpanded,
                onExpandedChange = { isLanguagesExpanded = !isLanguagesExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                        .fillMaxWidth(),
                    value = languagesFilterText,
                    onValueChange = { languagesFilterText = it },
                    label = { Text("Languages Spoken") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLanguagesExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = isLanguagesExpanded,
                    onDismissRequest = { isLanguagesExpanded = false }
                ) {
                    val filteredLanguages = languagesList.filter {
                        it.contains(
                            languagesFilterText,
                            ignoreCase = true
                        ) && !languagesSpoken.contains(it)
                    }
                    filteredLanguages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(language) },
                            onClick = {
                                onAddLanguage(language)
                                languagesFilterText = ""
                                isLanguagesExpanded = false
                            }
                        )
                    }
                }
            }
            if (languagesSpoken.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(languagesSpoken) { language ->
                        InputChip(
                            selected = true,
                            onClick = { onRemoveLanguage(language) },
                            label = { Text(language) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Remove Language",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Gender Dropdown
        ExposedDropdownMenuBox(
            expanded = isGenderExpanded,
            onExpandedChange = { isGenderExpanded = !isGenderExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth(),
                readOnly = true,
                value = gender,
                onValueChange = {},
                label = { Text("Gender") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = isGenderExpanded,
                onDismissRequest = { isGenderExpanded = false }
            ) {
                genders.forEach { genderOption ->
                    DropdownMenuItem(
                        text = { Text(genderOption) },
                        onClick = {
                            onGenderChange(genderOption)
                            isGenderExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewApplicationScreen(
    name: String,
    email: String,
    phoneNumber: String,
    aboutDoctor: String,
    docCategory: String,
    experienceYears: String,
    consultationFee: String,
    languagesSpoken: List<String>,
    gender: String,
    profileUri: Uri?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Review your application",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            "Is the information you have submitted correct?",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth(0.9f),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        ReviewSection(title = "Personal Information") {
            ProfileImage(profileUri = profileUri, onEditClick = {})
            Spacer(Modifier.height(16.dp))
            ReviewItem(label = "Full name", value = name)
            ReviewItem(label = "Email address", value = email)
            ReviewItem(label = "Phone Number", value = phoneNumber)
        }

        Spacer(Modifier.height(16.dp))

        ReviewSection(title = "Additional Information") {
            ReviewItem(label = "About Doctor", value = aboutDoctor)
            ReviewItem(label = "Specialization", value = docCategory)
            ReviewItem(label = "Years of Experience", value = buildString {
                append(experienceYears)
                append(" years")
            })
            ReviewItem(label = "Consultation Fee", value = buildString {
                append("$ ")
                append(consultationFee)
            })
            ReviewItem(label = "Languages Spoken", value = languagesSpoken.joinToString(", "))
            ReviewItem(label = "Gender", value = gender)
        }
    }
}

@Composable
fun ReviewSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = Color.Black)
            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Black)
        }
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
fun ReviewItem(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun JobApplicationFormPreview() {
    CompleteProfileScreen()

}
