package com.example.chatexample.data

import java.io.Serializable

class Group(
    var keyIdGroup: String?= "",
    var avatar : String? = "",
    var title : String? = "",
    var description: String? = ""

) : Serializable