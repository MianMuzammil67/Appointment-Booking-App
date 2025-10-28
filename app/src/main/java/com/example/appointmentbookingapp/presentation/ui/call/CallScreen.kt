package com.example.appointmentbookingapp.presentation.ui.call

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appointmentbookingapp.presentation.ui.appointment.AppointmentViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.AppointmentSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.UserRole
import java.util.UUID

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CallScreen(
    navController: NavController,
    callViewModel: CallViewModel = hiltViewModel(),
    appointmentSharedViewModel: AppointmentSharedViewModel,
    userRoleSharedViewModel: UserRoleSharedViewModel = hiltViewModel(),
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val logTag = "CallScreen"

    val currentAppointment by appointmentSharedViewModel.selectedAppointment.collectAsState()
    val userRole by userRoleSharedViewModel.userRole.collectAsState()
    val userId by appointmentViewModel.currentUserId.collectAsState()
    val callState by callViewModel.callState.collectAsState()

    val webViewRef = remember { mutableStateOf<WebView?>(null) }
    var isMuted by remember { mutableStateOf(false) }
    var isSwapped by remember { mutableStateOf(false) }

    var hasPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val appointmentId = currentAppointment?.appointmentId
    LaunchedEffect(appointmentId) {
        appointmentId?.let {
            callViewModel.observeCallState(it)
        }
    }
    if (appointmentId.isNullOrBlank() || userId.isNullOrBlank()) return

    Log.d(logTag, "UserId: $userId, Role: $userRole, AppointmentId: $appointmentId")

    when (callState) {
        CallState.ENDED -> navController.navigateUp()
        else -> {}
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissions->
            hasPermission = permissions[android.Manifest.permission.CAMERA] == true &&
                    permissions[android.Manifest.permission.RECORD_AUDIO] == true
            if (!hasPermission){
                Toast.makeText(context, "Camera & Microphone are required", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
        )
    }
    if (!hasPermission) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Requesting camera and microphone permissions...")
        }
        return
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                factory = { context ->
                    FrameLayout(context).apply {
                        addView(WebView(context).apply {
                            settings.javaScriptEnabled = true
                            settings.mediaPlaybackRequiresUserGesture = false
                            settings.allowFileAccess = true
                            settings.allowContentAccess = true

                            webChromeClient = object : WebChromeClient() {
                                override fun onPermissionRequest(request: PermissionRequest) {
                                    request.grant(request.resources)
                                }
                            }
                            webViewClient = object : WebViewClient() {

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    val deviceId = UUID.randomUUID().toString()
                                    val doctorPeerId = "${userId}_$deviceId"

                                    evaluateJavascript("init('$doctorPeerId')", null)
                                    Log.d(logTag, "init called: $doctorPeerId")
                                }
                            }

                            //   JavaScript interface to get callback when peer is connected
                            addJavascriptInterface(object {
                                @JavascriptInterface
                                fun onPeerConnected(peerId: String) {
                                    Log.d(logTag, "Doctor Peer ID: $peerId")
                                    callViewModel.updateCallState(appointmentId, CallState.STARTED)

                                    if (appointmentId.isNotBlank()) {
                                        callViewModel.updatePeerId(
                                            appointmentId = appointmentId,
                                            role = UserRole.DOCTOR,
                                            peerId = peerId
                                        )
                                    }
                                    // Once peer is connected, fetch patient peerId and start the call
                                    callViewModel.getPatientPeerId(appointmentId) { patientPeerId ->
                                        if (!patientPeerId.isNullOrBlank()) {
                                            webViewRef.value?.evaluateJavascript(
                                                "startCall('$patientPeerId')", null
                                            )
                                            Log.d(logTag, "startCall called: $patientPeerId")

                                        }
                                    }
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
                //  Swap Video (left)
                FloatingActionButton(
                    onClick = {
//                        webViewRef.value?.evaluateJavascript("toggleVideo(${isSwapped})", null)
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
                        navController.navigateUp()
                        callViewModel.updateCallState(appointmentId, CallState.ENDED)
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
                    containerColor = if (isMuted) Color.Gray else Color(0xFF43A047),
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