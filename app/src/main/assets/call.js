let localVideo = document.getElementById("local-video");
let remoteVideo = document.getElementById("remote-video");
localVideo.style.opacity = 0;
remoteVideo.style.opacity = 0;

localVideo.onplaying = () => { localVideo.style.opacity = 1 }
remoteVideo.onplaying = () => { remoteVideo.style.opacity = 1 }

let peer;
let localStream;
let currentCall = null;


// Initialize PeerJS
function init(userId) {
    console.log("Initializing peer with ID:", userId);

    if (peer) {
        peer.destroy();
    }

    const peerConfig = {
        config: {
            iceServers: [
                { urls: 'stun:stun.l.google.com:19302' },
                { urls: 'stun:stun1.l.google.com:19302' },
            ]
        }
    };

    peer = new Peer(userId, peerConfig);

    peer.on("open", id => {
        console.log("Peer open confirmed:", id);
        if (window.Android && window.Android.onPeerConnected) {
            window.Android.onPeerConnected(id);
        }
    });

    peer.on('error', (err) => {
        console.error("Peer error:", err);
    });

    peer.on('connection', () => {
        console.log("PeerJS connection established");
    });

    listen(); // Start listening for calls
}

// Listen for incoming call
function listen() {
    console.log("Listening for incoming calls...");

    peer.on('call', (call) => {
        console.log("Incoming call from:", call.peer);

    currentCall = call;


        navigator.mediaDevices.getUserMedia({ audio: true, video: true })
            .then((stream) => {
                console.log("Local media stream ready for answering call");

                localStream = stream;
                localVideo.srcObject = stream;

                call.answer(stream);
                console.log("Answered the call");

                call.on('stream', (remoteStream) => {
                    console.log("Remote stream received from caller");
                    remoteVideo.srcObject = remoteStream;

                    remoteVideo.className = "primary-video"
                    localVideo.className = "secondary-video"
                });

                call.on('close', () => {
                    console.log("Call ended");
                });

                call.on('error', (err) => {
                    console.error("Call error:", err);
                });

                // ICE state logs
                if (call.peerConnection) {
                    call.peerConnection.addEventListener("iceconnectionstatechange", () => {
                        console.log("ICE state (callee):", call.peerConnection.iceConnectionState);
                    });
                }

            })
            .catch((err) => {
                console.error("Error getting media for incoming call:", err);
            });
    });
}

// Start outgoing call
function startCall(otherUserId) {
    console.log("Starting call to:", otherUserId);

    navigator.mediaDevices.getUserMedia({ audio: true, video: true })
        .then((stream) => {
            console.log("Local media stream ready for outgoing call");

            localStream = stream;
            localVideo.srcObject = stream;

            const call = peer.call(otherUserId, stream);

            //
            currentCall = call;
            //

            call.on('stream', (remoteStream) => {
                console.log("Remote stream received from callee");
                remoteVideo.srcObject = remoteStream;

                remoteVideo.className = "primary-video"
                localVideo.className = "secondary-video"
            });

            call.on('close', () => {
                console.log("Call ended");
                  if (window.Android && window.Android.onCallEnded) {
                        window.Android.onCallEnded();
                  }
            });

            call.on('error', (err) => {
                console.error("Call error:", err);
            });

            //  ICE state logs
            if (call.peerConnection) {
                call.peerConnection.addEventListener("iceconnectionstatechange", () => {
                    console.log("ICE state (caller):", call.peerConnection.iceConnectionState);
                });
            }

        })
        .catch((err) => {
            console.error("Error getting media for outgoing call:", err);
        });
}

//// Toggle Video
//function toggleVideo(b) {
//    if (!localStream) return console.warn("No local stream for video toggle");
//    const videoTrack = localStream.getVideoTracks()[0];
//    if (videoTrack) videoTrack.enabled = b === "true";
//}
//
////  Toggle Audio
//function toggleAudio(b) {
//    if (!localStream) return console.warn("No local stream for audio toggle");
//    const audioTrack = localStream.getAudioTracks()[0];
//    if (audioTrack) audioTrack.enabled = b === "true";
//}

function toggleVideo(b) {
    if (b == "true") {
        localStream.getVideoTracks()[0].enabled = true
    } else {
        localStream.getVideoTracks()[0].enabled = false
    }
}

function toggleAudio(b) {
    if (b == "true") {
        localStream.getAudioTracks()[0].enabled = true
    } else {
        localStream.getAudioTracks()[0].enabled = false
    }
}



// Swap Video Layout

let isSwapped = false;
function swapVideos() {
//    if (!remoteVideo || !localVideo) return;
    if (!isSwapped) {
        remoteVideo.className = "secondary-video";
        localVideo.className = "primary-video";
    } else {
        remoteVideo.className = "primary-video";
        localVideo.className = "secondary-video";
    }
    isSwapped = !isSwapped;
}

function stopLocalStream() {
    if (localStream) {
        localStream.getTracks().forEach(track => track.stop());
        localStream = null;
        localVideo.srcObject = null;
    }
}

function endCall() {
    if (currentCall) {
        currentCall.close();
        currentCall = null;
    }
    stopLocalStream();
}
