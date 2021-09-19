package com.example.chatexample.ui.create_group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatexample.R
import com.example.chatexample.adapter.SimpleAdapter
import com.example.chatexample.adapter.SimpleDataBoundItemListener
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentCreateGroupBinding
import com.example.chatexample.databinding.FragmentUserBinding
import com.example.chatexample.ui.user.UserFragmentDirections
import com.example.chatexample.ui.user.UserListener
import com.example.chatexample.ui.user.UserViewModel
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupFragment : Fragment(), SimpleDataBoundItemListener,
    ValueEventListener, CreateGroupListener {
    private lateinit var userBinding: FragmentCreateGroupBinding
    private val viewModel : CreateGroupViewModel by viewModels()
    private var reference: DatabaseReference? = null
    private var referenceGroupChat: DatabaseReference? = null
    private val arr = mutableListOf<User>()
    private val args: CreateGroupFragmentArgs by navArgs()
    private val createGroupAdapter by lazy {
        SimpleAdapter<User>(layoutInflater, R.layout.item_create_group).apply {
            listener = this@CreateGroupFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_create_group,
            container,
            false
        )
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userBinding.apply {
            adapter = this@CreateGroupFragment.createGroupAdapter
            listener = this@CreateGroupFragment
        }
        reference = FirebaseDatabase.getInstance().getReference("user")
        referenceGroupChat = FirebaseDatabase.getInstance().getReference("group_chat")
        reference?.addValueEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) {
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            val user = it.getValue(User::class.java)
            if (viewModel.selfUser?.keyId != it.key) {
                user?.keyId = it.key
                user?.let {
                    arr.add(it)
                }
            }
        }
        createGroupAdapter.setData(arr)
    }

    override fun clickSelectUser(item: User) {
        item.isSelected = item.isSelected != true
        createGroupAdapter.notifyDataSetChanged()
    }

    override fun createGroupChat() {
        arr.forEach {
            if (it.isSelected == true) {
                it.keyIdGroup = args.listGroup.toInt().plus(1).toString()
                referenceGroupChat?.push()
                referenceGroupChat?.child(it.keyIdGroup.toString())?.child(it.keyId.toString())?.setValue(it)
            }
        }
        findNavController().navigateWithAnim(CreateGroupFragmentDirections.openGroupChatDetail())

    }
}