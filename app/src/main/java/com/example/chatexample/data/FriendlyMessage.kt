package com.example.chatexample.data

import java.io.Serializable

class FriendlyMessage (
    var text: String? = null,
    var name: String? = null,
    var photoUrl: String? = null
) : Serializable