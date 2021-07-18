package com.example.chatexample.ui.foward

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatexample.R
import com.example.chatexample.databinding.FragmentForwardBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForwardFragment : Fragment(), ForwardListener {
    private lateinit var forwardBinding: FragmentForwardBinding
    private val viewModel : ForwardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forwardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_forward,
            container,
            false
        )
        return forwardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forwardBinding.listener = this
        forwardBinding.apply {
            listener = this@ForwardFragment
        }
    }

    override fun onDetach() {
        super.onDetach()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    override fun openMainChat() {
        findNavController().navigateWithAnim(ForwardFragmentDirections.openMainChat())
    }

    override fun logout() {
        viewModel.logout()
        findNavController().popBackStack(R.id.homeFragment, false)
    }
}