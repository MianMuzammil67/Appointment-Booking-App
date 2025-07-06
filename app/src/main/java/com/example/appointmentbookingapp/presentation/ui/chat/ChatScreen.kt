package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.Message
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
) {
    var messageInput by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            Message(UUID.randomUUID().toString(), "Hey there!", false),
            Message(UUID.randomUUID().toString(), "Hi! How are you?", true),
            Message(
                UUID.randomUUID().toString(),
                "I'm doing great, thanks for asking! Just chilling.",
                false
            ),
            Message(UUID.randomUUID().toString(), "Nice! What's up?", true),
            Message(
                UUID.randomUUID().toString(),
                "Not much, just coding a chat app using Jetpack Compose. It's pretty cool!",
                false
            ),
            Message(UUID.randomUUID().toString(), "That sounds awesome! Is it difficult?", true),
            Message(
                UUID.randomUUID().toString(),
                "It has its moments, but overall it's quite intuitive. The declarative UI really speeds things up.",
                false
            ),
            Message(UUID.randomUUID().toString(), "Cool, cool. Maybe I should learn it too.", true),
            Message(UUID.randomUUID().toString(), "Definitely! You'd pick it up fast.", false),
            Message(
                UUID.randomUUID().toString(),
                "Okay, I'll check it out. Thanks for the info!",
                true
            )
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.im_doctor),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, colorResource(R.color.colorPrimary), CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "John Doe",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Online",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Message List
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    reverseLayout = true // To make new messages appear at the bottom
                ) {
                    items(messages.reversed()) { message -> // Displaying reversed for chronological order
                        MessageBubble(message = message)
                    }
                }

                // Message Input
                MessageInput(
                    message = messageInput,
                    onMessageChange = { messageInput = it },
                    onSendMessage = {
                        if (messageInput.isNotBlank()) {
                            messages.add(
                                Message(
                                    UUID.randomUUID().toString(),
                                    messageInput,
                                    true
                                )
                            )
                            messageInput = ""
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isSentByMe) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isSentByMe) colorResource(R.color.colorPrimary) else MaterialTheme.colorScheme.surfaceVariant
//                containerColor = if (message.isSentByMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = message.text,
                color = if (message.isSentByMe) colorResource(R.color.white) else MaterialTheme.colorScheme.onSurfaceVariant,
//                color = if (message.isSentByMe) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun MessageInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message...") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.colorPrimary),
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = colorResource(R.color.colorPrimary),
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = colorResource(R.color.colorPrimary)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onSendMessage,
            modifier = Modifier.size(48.dp),
            containerColor = colorResource(R.color.colorPrimary),
            contentColor = colorResource(R.color.white)
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Message")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen(navController = rememberNavController())
}