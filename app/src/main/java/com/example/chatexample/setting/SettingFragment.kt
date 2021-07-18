package com.example.chatexample.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatexample.R
import com.example.chatexample.databinding.FragmentSettingBinding
import com.example.chatexample.ui.utils.navigateWithAnim

class SettingFragment : Fragment(), SettingListener {
    private lateinit var settingBinding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.fragment_setting,
            container,
            false
        )
        return settingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingBinding.listener = this
    }

    override fun openChangeProfile() {
        findNavController().navigateWithAnim(SettingFragmentDirections.openEditProfile())
    }
}