package com.example.chatexample.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatexample.R
import com.example.chatexample.adapter.SimpleAdapter
import com.example.chatexample.adapter.SimpleDataBoundItemListener
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentChatBinding
import com.example.chatexample.ui.register.RegisterFragment
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(), SimpleDataBoundItemListener, ValueEventListener, ChatListener {
    private lateinit var chatBinding: FragmentChatBinding
    private val viewModel : ChatViewModel by viewModels()
    private var reference: Query? = null
    private val chatAdapter by lazy {
        SimpleAdapter<User>(layoutInflater, R.layout.item_chat).apply {
            listener = this@ChatFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_chat,
            container,
            false
        )
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBinding.adapter = chatAdapter
        reference = FirebaseDatabase.getInstance().getReference("chat").child(viewModel.selfUser?.keyId.toString())
        reference?.addValueEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) {}

    override fun onDataChange(snapshot: DataSnapshot) {
        val arr = mutableListOf<User>()
        snapshot.children.forEach {
            val user = it.getValue(User::class.java)
            user?.keyId = it.key
            user?.let {
                arr.add(it)
            }
        }
        chatAdapter.setData(arr)
    }

    override fun openChatDetail(user: User) {
        findNavController().navigateWithAnim(ChatFragmentDirections.openChatDetail(user))
    }
}