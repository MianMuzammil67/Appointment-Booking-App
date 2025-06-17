package com.example.appointmentbookingapp.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.appointmentbookingapp.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User,
    onEditProfileClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onAboutUsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                val painter: Painter = rememberAsyncImagePainter(model = user.profileUrl)
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Name
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // User Email
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Details Card (Basic Info)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileInfoRow(
                        icon = Icons.Default.Person,
                        label = "Full Name",
                        value = user.name
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email Address",
                        value = user.email
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons Card (Grouped for professionalism)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileActionItem(
                        icon = Icons.Default.Person,
                        text = "Edit Profile",
                        onClick = onEditProfileClick
                    )
                    HorizontalDivider() // Subtle divider
                    // Privacy Policy
                    ProfileActionItem(
                        icon = Icons.Default.PrivacyTip,
                        text = "Privacy Policy",
                        onClick = onPrivacyPolicyClick
                    )
                    HorizontalDivider()
                    // About Us
                    ProfileActionItem(
                        icon = Icons.Default.Info,
                        text = "About Us",
                        onClick = onAboutUsClick
                    )
                    HorizontalDivider()
                    // Logout
                    ProfileActionItem(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        text = "Logout",
                        onClick = onLogoutClick,
                        isDestructive = true // Indicates a destructive action
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProfileActionItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        headlineContent = {
            Text(
                text = text,
                color = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            if (!isDestructive) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val sampleUser = User(
        name = "John Doe",
        email = "john.doe@example.com",
        password = "SecurePassword",
        profileUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    )
    MaterialTheme {
        ProfileScreen(
            user = sampleUser,
            onEditProfileClick = { /* Preview action */ },
            onPrivacyPolicyClick = { /* Preview action */ },
            onAboutUsClick = { /* Preview action */ },
            onLogoutClick = { /* Preview action */ }
        )
    }
}




//package com.example.appointmentbookingapp.presentation.ui.profile
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.History
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Payment
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.PrivacyTip
//import androidx.compose.material.icons.filled.SupportAgent
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil3.compose.rememberAsyncImagePainter
//import com.example.appointmentbookingapp.domain.model.User
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    user: User,
//    onEditProfileClick: () -> Unit = {},
//    onAppointmentHistoryClick: () -> Unit = {},
//    onPaymentMethodsClick: () -> Unit = {},
//    onNotificationSettingsClick: () -> Unit = {},
//    onPrivacyPolicyClick: () -> Unit = {},
//    onHelpSupportClick: () -> Unit = {}, // New: Help & Support
//    onAboutUsClick: () -> Unit = {},
//    onLogoutClick: () -> Unit = {}
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Profile",
//                    fontWeight = FontWeight.Bold,
////                    color = MaterialTheme.colorScheme.onPrimary
//                ) },
////                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
//                actions = {
//                    IconButton(onClick = onEditProfileClick) {
//                        Icon(
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = "Edit Profile",
////                            tint = MaterialTheme.colorScheme.onPrimary
//                        )
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .background(MaterialTheme.colorScheme.background)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Profile Picture
//            Box(
//                modifier = Modifier
//                    .size(120.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.secondaryContainer)
//            ) {
//                val painter: Painter = rememberAsyncImagePainter(model = user.profileUrl)
//                Image(
//                    painter = painter,
//                    contentDescription = "Profile Picture",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // User Name
//            Text(
//                text = user.name,
//                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
//                color = MaterialTheme.colorScheme.onSurface
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // User Email
//            Text(
//                text = user.email,
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // User Account Information (Non-editable)
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp),
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Account Information",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    ProfileInfoRow(icon = Icons.Default.Person, label = "Full Name", value = user.name)
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//                    ProfileInfoRow(icon = Icons.Default.Email, label = "Email Address", value = user.email)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Appointments Section
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp),
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column {
//                    Text(
//                        text = "Appointments",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.History,
//                        text = "Appointment History",
//                        onClick = onAppointmentHistoryClick
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.Payment,
//                        text = "Payment Methods", // Or "Billing Information"
//                        onClick = onPaymentMethodsClick
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // General Settings & Support Section
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp),
//                shape = RoundedCornerShape(12.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column {
//                    Text(
//                        text = "Settings & Support",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.Notifications,
//                        text = "Notification Settings",
//                        onClick = onNotificationSettingsClick
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.PrivacyTip,
//                        text = "Privacy Policy",
//                        onClick = onPrivacyPolicyClick
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.SupportAgent, // Or Icons.Default.Help
//                        text = "Help & Support",
//                        onClick = onHelpSupportClick
//                    )
//                    Divider()
//                    ProfileActionItem(
//                        icon = Icons.Default.Info,
//                        text = "About Us",
//                        onClick = onAboutUsClick
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Logout Button (Standalone for clear action)
//            Button(
//                onClick = onLogoutClick,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .padding(horizontal = 8.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//            ) {
//                Text("Logout", color = MaterialTheme.colorScheme.onError)
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//        }
//    }
//}
//
//@Composable
//fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.size(24.dp)
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Column {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//            Text(
//                text = value,
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
//                color = MaterialTheme.colorScheme.onSurface
//            )
//        }
//    }
//}
//
//@Composable
//fun ProfileActionItem(
//    icon: ImageVector,
//    text: String,
//    onClick: () -> Unit
//) {
//    ListItem(
//        modifier = Modifier.clickable(onClick = onClick),
//        leadingContent = {
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        },
//        headlineContent = {
//            Text(
//                text = text,
//                color = MaterialTheme.colorScheme.onSurface
//            )
//        },
//        trailingContent = {
//            Icon(
//                imageVector = Icons.Default.KeyboardArrowRight,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewProfileScreen() {
//    val sampleUser = User(
//        name = "John Doe",
//        email = "john.doe@example.com",
//        password = "securepassword",
//        profileUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
//    )
//    MaterialTheme {
//        ProfileScreen(
//            user = sampleUser,
//            onEditProfileClick = { /* Preview action */ },
//            onAppointmentHistoryClick = { /* Preview action */ },
//            onPaymentMethodsClick = { /* Preview action */ },
//            onNotificationSettingsClick = { /* Preview action */ },
//            onPrivacyPolicyClick = { /* Preview action */ },
//            onHelpSupportClick = { /* Preview action */ },
//            onAboutUsClick = { /* Preview action */ },
//            onLogoutClick = { /* Preview action */ }
//        )
//    }
//}
