package com.example.appointmentbookingapp.presentation.ui.chat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

data class Message(val id: String, val text: String, val isSentByMe: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBackClicked: () -> Unit = {}
) {
    var messageInput by remember { mutableStateOf("") }
//    val messages = remember { mutableStateListOf<Message>() }
    val messages = remember {
        mutableStateListOf(
            Message(UUID.randomUUID().toString(), "Hey there!", false),
            Message(UUID.randomUUID().toString(), "Hi! How are you?", true),
            Message(UUID.randomUUID().toString(), "I'm doing great, thanks for asking! Just chilling.", false),
            Message(UUID.randomUUID().toString(), "Nice! What's up?", true),
            Message(UUID.randomUUID().toString(), "Not much, just coding a chat app using Jetpack Compose. It's pretty cool!", false),
            Message(UUID.randomUUID().toString(), "That sounds awesome! Is it difficult?", true),
            Message(UUID.randomUUID().toString(), "It has its moments, but overall it's quite intuitive. The declarative UI really speeds things up.", false),
            Message(UUID.randomUUID().toString(), "Cool, cool. Maybe I should learn it too.", true),
            Message(UUID.randomUUID().toString(), "Definitely! You'd pick it up fast.", false),
            Message(UUID.randomUUID().toString(), "Okay, I'll check it out. Thanks for the info!", true)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("John Doe") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
                            messages.add(Message(java.util.UUID.randomUUID().toString(), messageInput, true))
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
                containerColor = if (message.isSentByMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = message.text,
                color = if (message.isSentByMe) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
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
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary, // Optional: for label color
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Optional: for label color
                cursorColor = MaterialTheme.colorScheme.primary // Optional: for cursor color
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onSendMessage,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(Icons.Filled.Send, contentDescription = "Send Message")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}