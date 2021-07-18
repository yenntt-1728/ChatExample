package com.example.chatexample.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chatexample.R
import com.example.chatexample.adapter.SimpleAdapter
import com.example.chatexample.adapter.SimpleDataBoundItemListener
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentUserBinding
import com.google.firebase.database.*

class UserFragment : Fragment(), UserListener, SimpleDataBoundItemListener, ValueEventListener {
    private lateinit var userBinding: FragmentUserBinding
    private var reference: Query? = null
    private val userAdapter by lazy {
        SimpleAdapter<User>(layoutInflater, R.layout.item_user).apply {
            listener = this@UserFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_user,
            container,
            false
        )
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userBinding.apply {
            adapter = this@UserFragment.userAdapter
        }
        reference = FirebaseDatabase.getInstance().getReference("user")
        reference?.addValueEventListener(this)
    }


    override fun onItemUserClick() {
    }

    override fun onCancelled(error: DatabaseError) {
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        val arr = mutableListOf<User>()
        snapshot.children.forEach {
            val user = it.getValue(User::class.java)
            user?.let {
                arr.add(it)
            }
        }
        userAdapter.setData(arr)
    }
}