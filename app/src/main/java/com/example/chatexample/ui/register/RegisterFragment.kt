package com.example.chatexample.ui.register

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.example.chatexample.databinding.FragmentRegisterBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap

@AndroidEntryPoint
class RegisterFragment : Fragment(), RegisterListener, ValueEventListener{
    private val viewModel : RegisterViewModel by viewModels()
    private lateinit var registerBinding : FragmentRegisterBinding
    private var dialog : AlertDialog.Builder?  = null
    private var reference : DatabaseReference? = null
    private var listUser : Long? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.fragment_register, container, false)
        return registerBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog = AlertDialog.Builder(context)
        reference = FirebaseDatabase.getInstance().getReference("user")
        registerBinding.apply {
            viewModel = this@RegisterFragment.viewModel
            listener = this@RegisterFragment
        }
        registerBinding.viewModel = this.viewModel
        viewModel.registerSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                reference?.push()
                reference?.child(listUser?.plus(1).toString())?.setValue(User("", registerBinding.username.text.toString()))
                viewModel.setUserRegister()
                findNavController().navigateWithAnim(RegisterFragmentDirections.openForward())
            } else {
                dialog?.apply {
                    setMessage("Email or Password Invalidate \nPlease change")
                    setPositiveButton("OK") { dialog, _->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        })
        reference?.addValueEventListener(this)
    }

    override fun registerUser() {
        viewModel.registerUser((activity as MainActivity).auth)
    }

    override fun onCancelled(error: DatabaseError) {
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        listUser = snapshot.childrenCount
        Log.d("list_user", snapshot.childrenCount.toString())
    }
}