package com.example.appointmentbookingapp.presentation.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.ChatListItem
import com.example.appointmentbookingapp.ui.theme.mediumGray
import com.example.appointmentbookingapp.util.getRandomColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    chatListItem: List<ChatListItem>,
    onChatClick: (ChatListItem) -> Unit,
    onNewChatClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Conversations", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle search click */ }) {
                        Icon(Icons.Filled.Search, "Search chats")
                    }
                }
            )
        },
    ) { paddingValues ->
        if (chatListItem.isEmpty()) {
            EmptyChatListMessage(Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(chatListItem, key = { it.id }) { conversation ->
                    ChatListItem(conversation = conversation, onChatClick = onChatClick)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Composable
fun ChatListItem(conversation: ChatListItem, onChatClick: (ChatListItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChatClick(conversation) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        if (conversation.imageUrl != null) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Contact avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            )
        } else {
            // Default avatar with initial
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(getRandomColor()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.doctorName.first().toString(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = conversation.doctorName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = conversation.lastMessage,
                // Directly specifying style parameters
                fontSize = 14.sp,
                color = mediumGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = conversation.timestamp,
                fontSize = 12.sp,
                color = Color(0xFF757575)
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
    val sampleConversations = listOf(
        ChatListItem(
            id = "1",
            doctorName = "Alice Johnson",
            lastMessage = "Hey, how are you doing?",
            timestamp = "10:30 AM",
            unreadCount = 2,
            imageUrl = null
        ),
        ChatListItem(
            id = "2",
            doctorName = "Bob Smith",
            lastMessage = "I'll send you the details tomorrow.",
            timestamp = "Yesterday",
            unreadCount = 0,
            imageUrl = null
        ),
        ChatListItem(
            id = "3",
            doctorName = "Team Project",
            lastMessage = "Don't forget the meeting at 3 PM.",
            timestamp = "09:15 AM",
            unreadCount = 5,
            imageUrl = null
        ),
        ChatListItem(
            id = "4",
            doctorName = "Charlie Brown",
            lastMessage = "Sounds good!",
            timestamp = "Wed",
            unreadCount = 0,
            imageUrl = null
        ),
        ChatListItem(
            id = "5",
            doctorName = "Marketing Updates",
            lastMessage = "New campaign launched!",
            timestamp = "Mon",
            unreadCount = 1,
            imageUrl = null
        )
    )
    ChatListScreen(
        chatListItem = sampleConversations,
        onChatClick = {},
        onNewChatClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyChatListScreenPreview() {
    ChatListScreen(
        chatListItem = emptyList(),
        onChatClick = {},
        onNewChatClick = {}
    )
}
