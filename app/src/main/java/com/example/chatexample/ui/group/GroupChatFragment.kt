package com.example.chatexample.ui.group

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
import com.example.chatexample.data.Group
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentGroupChatBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupChatFragment : Fragment(), SimpleDataBoundItemListener,GroupChatItemListener, ValueEventListener, GroupChatListener {

    private lateinit var groupChatBinding: FragmentGroupChatBinding
    private val viewModel : GroupChatViewModel by viewModels()
    private var reference: Query? = null
    private val arr = mutableListOf<Group>()

    private val groupChatAdapter by lazy {
        SimpleAdapter<Group>(layoutInflater, R.layout.item_group_chat).apply {
            listener = this@GroupChatFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupChatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_group_chat,
            container,
            false
        )
        return groupChatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupChatBinding.apply {
            adapter = groupChatAdapter
            listenerClick = this@GroupChatFragment
        }
        reference = FirebaseDatabase.getInstance().getReference("group_chat").child(viewModel.selfUser?.keyIdGroup.toString())
        reference?.addValueEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) {}

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            val user = it.getValue(Group::class.java)
            user?.keyIdGroup = it.key
            user?.let {
                arr.add(it)
            }
        }
        groupChatAdapter.setData(arr)
    }

    override fun openCreateGroup() {
        findNavController().navigateWithAnim(GroupChatFragmentDirections.openCreateGroup(arr.size.toString()))
    }

    override fun openGroupChatDetail() {
        findNavController().navigateWithAnim(GroupChatFragmentDirections.openGroupChatDetail())
    }

    override fun onItemClick() {
        findNavController().navigateWithAnim(GroupChatFragmentDirections.openGroupChatDetail())
    }

}