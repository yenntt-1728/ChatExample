package com.example.chatexample.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chatexample.MainActivity
import com.example.chatexample.R
import com.example.chatexample.data.User
import com.example.chatexample.databinding.FragmentLoginBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginListener, ValueEventListener {
    private lateinit var loginBinding : FragmentLoginBinding
    private val viewModel : LoginViewModel by viewModels()
    private var dialog : AlertDialog.Builder?  = null
    private var reference : DatabaseReference? = null
    private lateinit var listUser : MutableList<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.fragment_login, container, false)
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding.apply {
            listener = this@LoginFragment
            viewModel = this@LoginFragment.viewModel
        }
        reference = FirebaseDatabase.getInstance().getReference("user")
        reference?.addValueEventListener(this)
        dialog = AlertDialog.Builder(context)
        viewModel.loginSuccessfully.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigateWithAnim(LoginFragmentDirections.openForward())
            } else {
                dialog?.apply {
                    setMessage("Login Fail")
                    setPositiveButton("OK") { dialog, _->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        })
    }

    override fun openRegisterUser() {
        findNavController().navigateWithAnim(LoginFragmentDirections.openRegister())
    }

    override fun loginUser() {
        viewModel.loginUser(listUser)
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        listUser = mutableListOf()
        snapshot.children.forEach {
            val user = it.getValue(User::class.java)
            user?.let { it1 -> listUser.add(it1) }
        }
    }
}