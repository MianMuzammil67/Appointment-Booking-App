package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.ConversationItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.util.Resource
import com.example.appointmentbookingapp.util.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val logTag: String = "ChatRemoteDataSource"

    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
    }

    suspend fun sendMessage(message: Message) {
        try {
            val batch = firestore.batch()

            val msgRef = firestore.collection("chats")
                .document(message.chatId)
                .collection("messages")
                .document(message.messageId)

            val docMsgRef = firestore.collection("doctors")
                .document(message.doctorId)
                .collection("conversations")
                .document(message.patientId)

            val patMsgRef = firestore.collection("users")
                .document(message.patientId)
                .collection("conversations")
                .document(message.doctorId)


            batch.set(msgRef, message)

            val patientData = mapOf(
                "doctorId" to message.doctorId,
                "lastMessage" to message.content,
                "timestamp" to message.timestamp
            )
            val doctorData = mapOf(
                "patientId" to message.patientId,
                "lastMessage" to message.content,
                "timestamp" to message.timestamp
            )

            batch.set(patMsgRef, patientData)
            batch.set(docMsgRef, doctorData)

            batch.commit().await()

            Log.d(logTag, "sendMessage: Message sent! ${message.content}")

        } catch (e: Exception) {
            Log.d(logTag, "sendMessage: ${e.message}")

        }
    }

    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {

        return firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Chat", "Listen failed: ${e.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null) {

                    val messages = snapshot.documents.mapNotNull {
                        it.toObject(Message::class.java)
                    }
                    onMessagesChanged(messages)
                }
            }
    }

    suspend fun getConversations(role: String): List<ConversationItem> {
        return try {

            val collection = when (role) {
                UserRole.DOCTOR -> "doctors"
                else -> "users"
            }
//            val conversations = firestore.collection("users")
            val conversations = firestore.collection(collection)
                .document(getCurrentUserId())
                .collection("conversations")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()

//            Log.d(logTag, conversations.toString())
            Log.d(logTag, "Fetched conversations: ${conversations.size()} items")

            conversations.documents.mapNotNull {
                it.toObject(ConversationItem::class.java)
            }
        } catch (e: Exception) {
            Log.d(logTag, "getConversations: $e")
            emptyList()
        }
    }

    suspend fun deleteMessage(message: Message): Resource<Unit> {
        return try {
            val batch = firestore.batch()

            // Message document reference
            val msgRef = firestore.collection("chats")
                .document(message.chatId)
                .collection("messages")
                .document(message.messageId)

            // Conversation metadata references
            val docMsgRef = firestore.collection("doctors")
                .document(message.doctorId)
                .collection("conversations")
                .document(message.patientId)

            val patMsgRef = firestore.collection("users")
                .document(message.patientId)
                .collection("conversations")
                .document(message.doctorId)

            batch.delete(msgRef)
            batch.commit().await()

//             check if any messages are left in the chat
            val messagesSnapshot = firestore.collection("chats")
                .document(message.chatId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            if (messagesSnapshot.isEmpty) {
//                No messages left
//                delete the conversation entries
                val cleanupBatch = firestore.batch()
                cleanupBatch.delete(docMsgRef)
                cleanupBatch.delete(patMsgRef)
                cleanupBatch.commit().await()
                Log.d(logTag, "deleteMessage: Last message deleted, conversation entries removed")
            } else {

//                Give me the 1 most recent message in this chat, ordered by timestamp descending
                val lastMsg = messagesSnapshot.documents.first().toObject(Message::class.java)
                if (lastMsg != null) {
                    val newPatientData = mapOf(
                        "doctorId" to lastMsg.doctorId,
                        "lastMessage" to lastMsg.content,
                        "timestamp" to lastMsg.timestamp
                    )
                    val newDoctorData = mapOf(
                        "patientId" to lastMsg.patientId,
                        "lastMessage" to lastMsg.content,
                        "timestamp" to lastMsg.timestamp
                    )

                    val updateBatch = firestore.batch()
                    updateBatch.set(patMsgRef, newPatientData)
                    updateBatch.set(docMsgRef, newDoctorData)
                    updateBatch.commit().await()

                    Log.d(logTag, "deleteMessage: lastMessage updated in conversation metadata")
                }
            }

            Resource.Success(Unit)

        } catch (e: Exception) {
            Log.e(logTag, "deleteMessage: ${e.message}")
            Resource.Error(e.localizedMessage ?: "Failed to delete message")
        }
    }

    suspend fun deleteConversation(otherUserId: String, role: String) {

        try {

            val collection = when (role) {
                UserRole.DOCTOR -> "doctors"
                else -> "users"
            }

            val msgRef = firestore.collection(collection)
                .document(getCurrentUserId())
                .collection("conversations")
                .document(otherUserId)

            msgRef.delete().await()

        } catch (e: Exception) {
            Log.d(logTag, "deleteConversation:${e.message}")
        }

    }


}

/*
    Suggestions (for future improvements):
    Add read receipts	To show "Seen" or "Delivered" states
    Add message type field	For supporting media (image, video, etc.)
    Cache user profiles (doctor/patient)	For faster chat list UI
    Add pagination for messages	Load 20 messages at a time for performance
 */
