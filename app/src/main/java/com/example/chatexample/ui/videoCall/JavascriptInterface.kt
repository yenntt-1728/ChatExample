package com.example.videocallapp

import android.webkit.JavascriptInterface
import com.example.chatexample.ui.videoCall.VideoCallFragment

class JavascriptInterface(val callActivity: VideoCallFragment) {

    @JavascriptInterface
    public fun onPeerConnected() {
        callActivity.onPeerConnected()
    }
}