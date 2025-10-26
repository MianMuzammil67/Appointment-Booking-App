package com.example.appointmentbookingapp.presentation.ui.call

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.ui.appointment.AppointmentViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.AppointmentSharedViewModel
import com.example.appointmentbookingapp.util.UserRole
import java.util.UUID

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WaitingRoomScreen(
    navController: NavController,
    callViewModel: CallViewModel,
    appointmentSharedViewModel: AppointmentSharedViewModel,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val appointment by appointmentSharedViewModel.selectedAppointment.collectAsState()
    val callState by callViewModel.callState.collectAsState()

    val currentUser by appointmentViewModel.currentUserId.collectAsState()
    val webViewRef = remember { mutableStateOf<WebView?>(null) }
    val logTag = "WaitingRoom"

    var isMuted by remember { mutableStateOf(false) }
    var isSwapped by remember { mutableStateOf(false) }

    val deviceId = remember { UUID.randomUUID().toString() }
    val patientPeerId = "${"pat"}_${currentUser}_$deviceId"
    var isCallActive by remember { mutableStateOf(false) }

    val appointmentId = appointment?.appointmentId
    LaunchedEffect(appointmentId) {
        appointmentId?.let {
            callViewModel.observeCallState(it)
        }
    }


    when(callState){
        CallState.STARTED -> isCallActive = true
        CallState.ENDED -> navController.navigateUp()
        else -> {}
    }

    Scaffold { paddingValues ->
        if (!isCallActive) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .alpha(if (isCallActive) 0f else 1f),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(0.9f)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            color = colorResource(R.color.colorPrimary)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Hold on, we're getting things ready...",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Your doctor will join the call shortly.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .alpha(if (isCallActive) 1f else 0f)
        ) {
            AndroidView(
                factory = { context ->
                    FrameLayout(context).apply {
                        addView(WebView(context).apply {
                            settings.javaScriptEnabled = true
                            settings.mediaPlaybackRequiresUserGesture = false
                            settings.allowFileAccess = true
                            settings.allowContentAccess = true
                            settings.domStorageEnabled = true
                            webChromeClient = object : WebChromeClient() {
                                override fun onPermissionRequest(request: PermissionRequest) {
                                    request.grant(request.resources)
                                }
                            }

                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    if (!currentUser.isNullOrBlank()) {

//                                    val deviceId = UUID.randomUUID().toString()
//                                    val patientPeerId = "${currentUser}_$deviceId"

                                        view?.evaluateJavascript("init('$patientPeerId');", null)

                                    } else {
                                        Log.e(logTag, "User ID is null or empty")
                                    }
                                }
                            }
                            addJavascriptInterface(object {
                                @JavascriptInterface
                                fun onPeerConnected(peerId: String) {
                                    Log.d("WaitingRoom", "Patient Peer ID: $peerId")

                                    if (!appointmentId.isNullOrBlank()) {
                                        callViewModel.updatePeerId(
                                            appointmentId = appointmentId,
                                            role = UserRole.PATIENT,
                                            peerId = patientPeerId
                                        )
                                    }
                                    callViewModel.updateCallState(
                                        appointmentId.toString(),
                                        CallState.WAITING
                                    )
                                }
                            }, "Android")
                            loadUrl("file:///android_asset/call.html")
                            webViewRef.value = this
                        })
                    }
                },
                modifier = Modifier.matchParentSize()
            )


            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //  Swap Video
                FloatingActionButton(
                    onClick = {
                        webViewRef.value?.evaluateJavascript("swapVideos()", null)
                        isSwapped = !isSwapped
                    },
                    containerColor = Color(0xFF1E88E5),
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        Icons.Default.FlipCameraAndroid,
                        contentDescription = "Swap Video",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                // End Call (Center)
                FloatingActionButton(
                    onClick = {
                        webViewRef.value?.evaluateJavascript("endCall()", null)

                        callViewModel.updateCallState(appointmentId!!, CallState.ENDED)
                        navController.navigateUp()
                    },
                    containerColor = Color.Red,
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                // Mute / Unmute (Right)
                FloatingActionButton(
                    onClick = {
                        isMuted = !isMuted
                        webViewRef.value?.evaluateJavascript("toggleAudio('${!isMuted}')", null)
                    },
                    containerColor = if (isMuted) Color.Gray else Color(0xFF43A047), // Green when active
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = "Mute / Unmute",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
