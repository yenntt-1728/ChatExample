package com.example.chatexample.ui.chat_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatexample.R
import com.example.chatexample.adapter.ChatMessageAdapter
import com.example.chatexample.adapter.MyScrollToBottomObserver
import com.example.chatexample.adapter.SimpleAdapter
import com.example.chatexample.adapter.SimpleDataBoundItemListener
import com.example.chatexample.data.FriendlyMessage
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentChatDetailBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ChatDetailFragment : Fragment(), ChatDetailListener, ValueEventListener, SimpleDataBoundItemListener {
    private val viewModel : ChatDetailViewModel by viewModels()
    private lateinit var chatDetailBinding : FragmentChatDetailBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var manager: LinearLayoutManager
    private lateinit var selfUser: User
    private lateinit var user : User
    private lateinit var listMessage : MutableList<FriendlyMessage>

    private val chatAdapter by lazy {
        SimpleAdapter<FriendlyMessage>(layoutInflater, R.layout.message).apply {
            listener = this@ChatDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()),
            R.layout.fragment_chat_detail,
            container,
            false)
        return chatDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : ChatDetailFragmentArgs by navArgs()
        user = args.user
        viewModel.getSelfUser()?.apply {
            selfUser = this
        }
        db = Firebase.database
        db.reference.child("messages").child(user.keyId.toString()).addValueEventListener(this)
        chatDetailBinding.apply {
            listener = this@ChatDetailFragment
            adapter = chatAdapter
        }
    }

    override fun sendMessage() {
        val friendlyMessage = FriendlyMessage(
            chatDetailBinding.messageEditText.text.toString(),
            selfUser.username,
            selfUser.avatar
        )
        db.reference.child("messages").child(user.keyId ?: "")
            .child(listMessage.size.plus(1).toString()).setValue(friendlyMessage)
        db.reference.child("chat").child(selfUser.keyId.toString()).child(user.keyId ?: "").setValue(user)
        chatDetailBinding.messageEditText.setText("")
    }

    private fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        listMessage = mutableListOf<FriendlyMessage>()
        snapshot.children.forEach {
            val message = it.getValue(FriendlyMessage::class.java)
            message?.apply {
                listMessage.add(this)
            }
        }
        chatAdapter.setData(listMessage)
        chatDetailBinding.messageRecyclerView.smoothScrollToPosition(0)
        chatAdapter.notifyDataSetChanged()
    }
}