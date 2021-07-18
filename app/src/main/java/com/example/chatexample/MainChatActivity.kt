package com.example.chatexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.chatexample.databinding.ActivityMainChatBinding
import com.example.chatexample.ui.MainConfig
import com.example.chatexample.ui.utils.supportFindNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainChatActivity : AppCompatActivity(), MainConfig {
    private lateinit var mainChatActivityBiding : ActivityMainChatBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainChatActivityBiding = DataBindingUtil.setContentView(this, R.layout.activity_main_chat)
        navController = supportFindNavController(R.id.nav_host_main_fragment).apply {
            addOnDestinationChangedListener { _, _, _ ->
                mainChatActivityBiding.config = this@MainChatActivity
            }
        }
        NavigationUI.setupWithNavController(mainChatActivityBiding.bottomNavigation, navController)
    }

    override val title: String
    get() {
        return when (navController.currentDestination?.id) {
            R.id.chatFragment -> "Chats"
            R.id.userFragment -> "Users"
            R.id.settingFragment -> "Mores"
            else -> "Calls"
        }
    }
}