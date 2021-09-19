package com.example.chatexample.ui.create_group

import com.example.chatexample.data.User

interface CreateGroupListener {
    fun clickSelectUser(item : User)
    fun createGroupChat()
}