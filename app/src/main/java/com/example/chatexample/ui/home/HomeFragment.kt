package com.example.chatexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatexample.R
import com.example.chatexample.databinding.FragmentHomeBinding
import com.example.chatexample.ui.utils.navigateWithAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeListener {
    private lateinit var homeBinding : FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_home,
            container,
            false
        )
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding.apply {
            listener = this@HomeFragment
            viewModel = this@HomeFragment.viewModel
        }
    }

    override fun openLogin() {
//        findNavController().navigateWithAnim(HomeFragmentDirections.openLogin())
        if (viewModel.isLogin()) {
            findNavController().navigateWithAnim(HomeFragmentDirections.openForward())
        } else {
            findNavController().navigateWithAnim(HomeFragmentDirections.openLogin())
        }
    }
}