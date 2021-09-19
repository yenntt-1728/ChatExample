package com.example.chatexample.ui.group_chat_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatexample.R
import com.example.chatexample.adapter.SimpleAdapter
import com.example.chatexample.adapter.SimpleDataBoundItemListener
import com.example.chatexample.data.FriendlyMessage
import com.example.chatexample.databinding.FragmentGroupChatDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupChatDetailFragment : Fragment(), ValueEventListener, SimpleDataBoundItemListener, GroupChatDetailListener {
    private val viewModel : GroupChatDetailViewModel by viewModels()
    private lateinit var chatDetailBinding : FragmentGroupChatDetailBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var manager: LinearLayoutManager
    private lateinit var listMessage : MutableList<FriendlyMessage>

    private val chatAdapter by lazy {
        SimpleAdapter<FriendlyMessage>(layoutInflater, R.layout.message).apply {
            listener = this@GroupChatDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_group_chat_detail,
            container,
            false)
        return chatDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.database
        db.reference.child("message_group").child(viewModel.getSelfUser()?.keyIdGroup.toString()).addValueEventListener(this)
        chatDetailBinding.apply {
            listener = this@GroupChatDetailFragment
            adapter = chatAdapter
        }
    }

    override fun sendMessage() {
        val friendlyMessage = FriendlyMessage(
            chatDetailBinding.messageEditText.text.toString(),
            viewModel.getSelfUser()?.username,
            viewModel.getSelfUser()?.avatar
        )
        db.reference.child("message_group").child(viewModel.getSelfUser()?.keyIdGroup ?: "").child(listMessage.size.plus(1).toString())
            .setValue(friendlyMessage)
        db.reference.child("chat").child(viewModel.getSelfUser()?.keyIdGroup ?: "").child(viewModel.getSelfUser()?.keyId.toString()).setValue(viewModel.getSelfUser())
        chatDetailBinding.messageEditText.setText("")
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