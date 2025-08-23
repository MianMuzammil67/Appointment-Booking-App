package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.presentation.ui.components.DeleteItemDialog
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.DoctorChatSharedViewModel
import com.example.appointmentbookingapp.ui.theme.mediumGray
import com.example.appointmentbookingapp.util.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    chatListViewModel: ChatListViewModel = hiltViewModel(),
    doctorChatSharedViewModel: DoctorChatSharedViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        chatListViewModel.getChatList()
    }

    var selectedItem by remember { mutableStateOf<ChatListItem?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val chatListResource by chatListViewModel.chatList.collectAsState()

    val isLoading = chatListResource is Resource.Loading
    val chatList = (chatListResource as? Resource.Success)?.data
    val errorMessage = (chatListResource as? Resource.Error)?.message

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (selectedItem != null) "1 selected" else "My Conversations",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* Handle search click */ }) {
                        Icon(Icons.Filled.Search, "Search chats")
                    }
                    selectedItem?.let {
                        IconButton(onClick = {
                            showDeleteDialog = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }

                }
            )
        },
    ) { paddingValues ->

        if (showDeleteDialog && selectedItem != null) {
            DeleteItemDialog(
                onDismiss = { showDeleteDialog = false },
                onConfirm = {
                    chatListViewModel.deleteConversation(selectedItem!!.doctor.id)
                    selectedItem = null
                    showDeleteDialog = false
                }
            )

        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: $errorMessage", color = Color.Red)
                }
            }

            chatList.isNullOrEmpty() -> {
                EmptyChatListMessage(Modifier.padding(paddingValues))
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(chatList, key = { it.id }) { conversation ->
                        ConversationCard(
                            conversation = conversation,
                            onChatClick = {
                                if (selectedItem != null) {
                                    selectedItem = null
                                } else {
                                    doctorChatSharedViewModel.updateCurrentDoctor(conversation.doctor)
                                    navController.navigate("ChatScreen")
                                }
                            },
                            longClickOnChat = { selectedItem = it },
                            isSelected = selectedItem?.id == conversation.id
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationCard(
    conversation: ChatListItem,
    onChatClick: (ChatListItem) -> Unit,
    longClickOnChat: (ChatListItem) -> Unit,
    isSelected: Boolean
) {
    val backgroundColor =
        if (isSelected) colorResource(R.color.colorPrimary).copy(0.5f) else Color.Transparent

    val currentDoctor = conversation.doctor
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .combinedClickable(
                onClick = { onChatClick(conversation) },
                onLongClick = {
                    longClickOnChat(conversation)
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        AsyncImage(
            model = currentDoctor.imageUrl,
            contentDescription = "Contact avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = currentDoctor.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = conversation.lastMessage,
                fontSize = 14.sp,
                color = if (isSelected) Color.White else mediumGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = conversation.timestamp,
                fontSize = 12.sp,
                color = if (isSelected) Color.White else Color(0xFF757575)
            )
            if (conversation.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Badge(
                    containerColor = Color(0xFFD32F2F), // Red for error/unread
                    contentColor = Color.White
                ) {
                    Text(text = conversation.unreadCount.toString())
                }
            }
        }
    }
}

@Composable
fun EmptyChatListMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "No Conversations",
            modifier = Modifier.size(96.dp),
            tint = Color(0xFFBDBDBD)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Conversations yet!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF424242)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
    ChatListScreen(
        navController = rememberNavController()
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyChatListScreenPreview() {
    ChatListScreen(
        navController = rememberNavController()
    )
}
