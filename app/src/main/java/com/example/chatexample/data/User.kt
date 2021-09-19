package com.example.chatexample.data

import java.io.Serializable

class User (
//    var id: Int = 0,
    var avatar : String? = "",
    var username : String? = "",
    var description: String? = "",
//    val time: Long? = 0,
    var email: String? = "",
    var password: String? = "",
    var keyId: String? = "",
    var keyIdGroup: String? = "",
    var isSelected: Boolean? = false
) : Serializable