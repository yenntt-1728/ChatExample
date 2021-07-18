package com.example.chatexample.ui.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.supportFindNavController(@IdRes viewId: Int) = (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController
