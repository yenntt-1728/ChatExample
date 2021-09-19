package com.example.chatexample.ui.list_call

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
import com.example.chatexample.databinding.FragmentListCallBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCallFragment : Fragment(), SimpleDataBoundItemListener, ValueEventListener, ListCallListener {
    private lateinit var chatBinding: FragmentListCallBinding
    private val viewModel : ListCallViewModel by viewModels()
    private var reference: Query? = null
    private val chatAdapter by lazy {
        SimpleAdapter<User>(layoutInflater, R.layout.item_call).apply {
            listener = this@ListCallFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_list_call,
            container,
            false
        )
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBinding.adapter = chatAdapter
        reference = FirebaseDatabase.getInstance().getReference("call").child(viewModel.selfUser?.keyId.toString())
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

    override fun openVideoCallUser(item: User) {
        findNavController().navigateWithAnim(ListCallFragmentDirections.openVideoCall(item))
    }
}