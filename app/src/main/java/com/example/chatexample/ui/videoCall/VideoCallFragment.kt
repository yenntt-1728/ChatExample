package com.example.chatexample.ui.videoCall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.chatexample.R
import com.example.chatexample.databinding.FragmentVideoCallBinding
import com.example.chatexample.ui.chat_detail.ChatDetailFragmentArgs
import com.example.videocallapp.JavascriptInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class VideoCallFragment : Fragment(), VideoCallListener {
    private lateinit var videoCallBinding: FragmentVideoCallBinding
    private val viewModel : VideoCallViewModel by viewModels()
    private var reference: Query? = null
    var isPeerConnected = false

    var firebaseRef = Firebase.database.getReference("user")

    var isAudio = true
    var isVideo = true
    var uniqueId = ""
    var username = ""
    var friendsUsername = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        videoCallBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_video_call,
            container,
            false
        )
        return videoCallBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = viewModel.selfUser?.keyId ?: ""
        val args : VideoCallFragmentArgs by navArgs()
        friendsUsername = args.user.keyId ?: ""
        videoCallBinding.listener = this
        setupWebView()
    }

    override fun callVideo() {
//        if (!isPeerConnected) {
//            Toast.makeText(activity, "You're not connected. Check your internet", Toast.LENGTH_LONG).show()
//            return
//        }

        firebaseRef.child(friendsUsername).child("incoming").setValue(friendsUsername)
        firebaseRef.child(friendsUsername).child("isAvailable").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.value.toString() == "true") {
                    listenForConnId()
                }

            }

        })
    }

    private fun listenForConnId() {
        firebaseRef.child(friendsUsername).child("connId").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null)
                    return
                switchToControls()
                callJavascriptFunction("javascript:startCall(\"${snapshot.value}\")")
            }

        })
    }

    private fun callJavascriptFunction(functionString: String) {
        videoCallBinding.webView.post { videoCallBinding.webView.evaluateJavascript(functionString, null) }
    }

    private fun setupWebView() {
        videoCallBinding.webView.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }
        }
        videoCallBinding.webView.apply {
            settings.javaScriptEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            addJavascriptInterface(JavascriptInterface(this@VideoCallFragment), "Android")
        }

        loadVideoCall()
    }

    private fun loadVideoCall() {
        val filePath = "file:android_asset/call.html"
        videoCallBinding.webView.loadUrl(filePath)

        videoCallBinding.webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                initializePeer()
            }
        }
    }

    private fun initializePeer() {
        uniqueId = getUniqueID()
        callJavascriptFunction("javascript:init(\"${uniqueId}\")")
        firebaseRef.child(viewModel.selfUser?.keyId ?: "").child("incoming").addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                onCallRequest(snapshot.value as? String)
            }

        })

    }

    private fun onCallRequest(caller: String?) {
        if (caller == null) return

        videoCallBinding.callLayout.visibility = View.VISIBLE
        videoCallBinding.incomingCallTxt.text = "$caller is calling..."

        videoCallBinding.acceptBtn.setOnClickListener {
            firebaseRef.child(username).child("connId").setValue(uniqueId)
            firebaseRef.child(username).child("isAvailable").setValue(true)

            videoCallBinding.callLayout.visibility = View.GONE
            switchToControls()
        }

        videoCallBinding.rejectBtn.setOnClickListener {
            firebaseRef.child(username).child("incoming").setValue(null)
            videoCallBinding.callLayout.visibility = View.GONE
        }
    }

    private fun switchToControls() {
        videoCallBinding.inputLayout.visibility = View.GONE
        videoCallBinding.callControlLayout.visibility = View.VISIBLE
    }

    private fun getUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    fun onPeerConnected() {
        isPeerConnected = true
    }

    override fun onDestroy() {
        firebaseRef.child(username).setValue(null)
        videoCallBinding.webView.loadUrl("about:blank")
        super.onDestroy()
    }
}