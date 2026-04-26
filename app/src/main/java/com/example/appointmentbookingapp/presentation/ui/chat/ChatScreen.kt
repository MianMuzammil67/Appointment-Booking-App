package com.example.appointmentbookingapp.presentation.ui.chat

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.VideoCall
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.domain.model.User
import com.example.appointmentbookingapp.presentation.ui.aiLogic.AiLogicViewModel
import com.example.appointmentbookingapp.presentation.ui.components.DeleteItemDialog
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.DoctorChatSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel(),
    doctorChatSharedViewModel: DoctorChatSharedViewModel = hiltViewModel(),
    userRoleSharedViewModel: UserRoleSharedViewModel = hiltViewModel(),
    aiLogicViewModel: AiLogicViewModel = hiltViewModel()
) {
    val otherUser = doctorChatSharedViewModel.currentUser
    val aiMessages = remember { mutableStateListOf<Message>() }

    val userRole by userRoleSharedViewModel.userRole.collectAsState()
    var messageInput by remember { mutableStateOf("") }
    val messages by chatViewModel.messages.collectAsState()

    var selectedMsg by remember { mutableStateOf<Message?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val aiResponse by aiLogicViewModel.result.collectAsState()
    val isAiLoading by aiLogicViewModel.isLoading.collectAsState()

    val name: String
    val imageUrl: String
    val userId: String

    when (otherUser) {
        is DoctorItem -> {
            name = otherUser.name
            imageUrl = otherUser.imageUrl
            userId = otherUser.id
        }

        is User -> {
            name = otherUser.name
            imageUrl = otherUser.profileUrl
            userId = otherUser.id
        }

        else -> {
            name = "Consult with AI"
            imageUrl =
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDk14dhpW6pbdUvNZnwzSZ6MJRgGRWtm2IAQ&s"
            userId = ""
        }
    }

    LaunchedEffect(Unit) {
        chatViewModel.listenToMessages(userId)
    }
    LaunchedEffect(aiResponse) {
        aiResponse?.text?.let {

            aiMessages.add(
                Message(
                    messageId = System.currentTimeMillis().toString(),
                    senderId = "AI",
                    content = it,
                    timestamp = System.currentTimeMillis(),
                    doctorId = "",
                    patientId = ""
                )
            )
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            doctorChatSharedViewModel.clearViewModel()
            Log.d("ChatScreen", "ChatScreen destroyed")
        }
    }


    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedMsg != null) {
                    Text(
                        text = "1 selected", fontWeight = FontWeight.Bold
                    )
                } else {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, colorResource(R.color.colorPrimary), CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = name,
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
            }
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, actions = {
            if (selectedMsg != null) {
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            } else {
                IconButton(onClick = {
                    navController.navigate("cal")

                }) {
                    Icon(Icons.Default.VideoCall, contentDescription = "Delete")
                }
            }
        }

        )
    }, content = { paddingValues ->
        if (showDeleteDialog && selectedMsg != null) {
            DeleteItemDialog(onDismiss = { showDeleteDialog = false }, onConfirm = {
                chatViewModel.deleteMessage(selectedMsg!!)
                selectedMsg = null
                showDeleteDialog = false
            })
        }

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
                reverseLayout = false
            ) {
                val allMessages = if (userId.isEmpty()) aiMessages else messages

                items(allMessages) { message ->
                    MessageBubble(
                        message = message,
                        isSentByMe = message.senderId == chatViewModel.currentUserId,
                        onMessageLongClick = {
                            selectedMsg = message
                        },
                        isSelected = selectedMsg?.messageId == message.messageId
                    )
                    Log.d(
                        "MessageDebug", """ CurrentUserId: ${chatViewModel.currentUserId}
                            CurrentUserRole: $userRole Message.patientId: ${message.patientId} 
                             Message.doctorId: ${message.doctorId}""".trimIndent()
                    )
                }
                if (userId.isEmpty() && isAiLoading) {
                    item {
                        MessageBubble(
                            message = Message(
                                messageId = "typing",
                                senderId = "AI",
                                content = "typing",
                                timestamp = System.currentTimeMillis(),
                                doctorId = "",
                                patientId = ""
                            ),
                            isSentByMe = false,
                            onMessageLongClick = {},
                            isSelected = false, isTyping = true
                        )
                    }
                }
            }

            // Message Input
            MessageInput(
                message = messageInput,
                onMessageChange = { messageInput = it },
                onSendMessage = {

                    if (messageInput.isBlank()) return@MessageInput

                    if (userId.isEmpty()) {
                        aiMessages.add(
                            Message(
                                messageId = System.currentTimeMillis().toString(),
                                senderId = chatViewModel.currentUserId,
                                content = messageInput,
                                timestamp = System.currentTimeMillis(),
                                doctorId = "",
                                patientId = "",

                            )
                        )
                        val msg = buildDoctorAssistantPrompt(messageInput)
                        aiLogicViewModel.getSuggestions(msg)

                    } else {

                        chatViewModel.sendMessage(
                            receiverId = userId,
                            messageContent = messageInput,
                            currentUserRole = userRole
                        )
                    }
                    messageInput = ""

//                        if (messageInput.isNotBlank()) {
//                            chatViewModel.sendMessage(
//                                receiverId = userId,
//                                messageContent = messageInput,
//                                currentUserRole = userRole
//                            )
//                            messageInput = ""
//                        }
                }
            )
        }
    })
}

@Composable
fun TypingDots() {

    val dotCount = 3
    val delayUnit = 300

    val infiniteTransition = rememberInfiniteTransition(label = "")

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(dotCount) { index ->

            val scale by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600,
                        delayMillis = index * delayUnit
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .size((6 * scale).dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageBubble(
    message: Message,
    isSentByMe: Boolean,
    onMessageLongClick: (message: Message) -> Unit,
    isSelected: Boolean,
    isTyping: Boolean = false

) {
    val bubbleColor = if (isSentByMe) {
        colorResource(id = R.color.colorPrimary)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (isSentByMe) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val backgroundColor =
        if (isSelected) colorResource(R.color.colorPrimary).copy(0.5f) else Color.Transparent

    val instant = Instant.ofEpochMilli(message.timestamp)
    val formatter = DateTimeFormatter.ofPattern("h:mm a").withZone(ZoneId.systemDefault())
    val formattedTime = formatter.format(instant)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .combinedClickable(onClick = {}, onLongClick = { onMessageLongClick(message) }),

        horizontalArrangement = if (isSentByMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.widthIn(max = 280.dp),
            horizontalAlignment = if (isSentByMe) Alignment.End else Alignment.Start
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = if (isSentByMe) 4.dp else 16.dp, // Smaller corner for the tail effect
                    bottomStart = if (isSentByMe) 16.dp else 4.dp
                ),
                colors = CardDefaults.cardColors(containerColor = bubbleColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {

                if (isTyping) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        TypingDots()
                    }
                } else {

                    Text(
                        text = message.content,
                        color = textColor,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        fontSize = 16.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = formattedTime,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )

// ⚠️ KEEP: Might be used later for showing read receipts
// ⚠️ DO NOT DELETE unless confirmed unnecessary

//                if (isSentByMe) {
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Icon(
//                        imageVector = Icons.Default.DoneAll, // Double checkmark for 'read'
//                        contentDescription = "Read",
//                        tint = MaterialTheme.colorScheme.primary, // A distinct color for 'read' status
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
            }
        }
    }
}

@Composable
fun MessageInput(
    message: String, onMessageChange: (String) -> Unit, onSendMessage: () -> Unit
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


fun buildDoctorAssistantPrompt(
    userText: String,
    doctorSpecialty: String = "general medical guidance"
): String {

    return """
        SYSTEM ROLE:
        You are a professional AI medical assistant inside a doctor consultation app.

        DOCTOR SPECIALIZATION:
        $doctorSpecialty

        STRICT RULES:
        • Only answer questions related to $doctorSpecialty
        • Provide educational medical guidance only
        • Do NOT provide diagnosis
        • Do NOT prescribe medication
        • Do NOT replace a real doctor consultation
        • If symptoms appear serious advise immediate consultation with a doctor
        • If question is unrelated reply EXACTLY with:

        "I can only help with $doctorSpecialty-related medical questions."

        RESPONSE STYLE:
        • Clear
        • Short
        • Supportive
        • Safe
        • Professional

        USER QUESTION:
        $userText
    """.trimIndent()
}


@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
//    ChatScreen(navController = rememberNavController())
}