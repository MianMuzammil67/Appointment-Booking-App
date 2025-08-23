package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.ConversationItem
import com.example.appointmentbookingapp.domain.model.Message
import com.example.appointmentbookingapp.util.Resource
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

            Log.d(logTag, "sendMessage: Message sent!")

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

    suspend fun getConversations(): List<ConversationItem> {
        return try {
            val conversations = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("conversations")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()

            Log.d(logTag, conversations.toString())

            conversations.documents.mapNotNull {
                it.toObject(ConversationItem::class.java)
            }
        } catch (e: Exception) {
            Log.d(logTag, "getConversations: ${e.message}")
            emptyList()
        }
    }
}
    suspend fun deleteConversation(doctorId: String) {

        try {
            val msgRef = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("conversations")
                .document(doctorId)

            msgRef.delete().await()

        }catch (e: Exception){
            Log.d(logTag,"deleteConversation:${e.message}")
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
