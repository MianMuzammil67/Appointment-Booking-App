package com.example.appointmentbookingapp.data.remorte

import android.util.Log
import com.example.appointmentbookingapp.domain.model.Message
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
//        return firebaseAuth.currentUser?.uid.toString()
    }

    suspend fun sendMessage(doctorId: String, message: Message) {
        try {
            val batch = firestore.batch()
            val chatId = generateChatId(doctorId, getCurrentUserId())


            val msgRef = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .document(message.messageId)

            val docMsgRef = firestore.collection("doctors")
                .document(doctorId)
                .collection("conversations")
                .document(getCurrentUserId())

            val patMsgRef = firestore.collection("users")
                .document(getCurrentUserId())
                .collection("conversations")
                .document(doctorId)


            batch.set(msgRef, message)

            val patientData = mapOf(
                "doctorId" to doctorId,
                "lastMessage" to message.content,
                "timestamp" to message.timestamp
            )
            val doctorData = mapOf(
                "patientId" to getCurrentUserId(),
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
        doctorId: String,
        patientId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {
        val chatId = generateChatId(doctorId, getCurrentUserId())

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

    fun generateChatId(userId1: String, userId2: String): String {
        return listOf(userId1, userId2).sorted().joinToString("_")
    }
}

/*
    Suggestions (for future improvements):
    Add read receipts	To show "Seen" or "Delivered" states
    Add message type field	For supporting media (image, video, etc.)
    Cache user profiles (doctor/patient)	For faster chat list UI
    Add pagination for messages	Load 20 messages at a time for performance
 */


