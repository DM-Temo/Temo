package com.example.temo.model

import android.net.Uri


data class User(
    var userName: String = "",
    var userId: String = "",
    var appCount: Int = 0
)

data class App(
    val userId: String = "",
    var appName: String = "Loading..",
    var creator: String = "Loading..",
    val postDate: String = "",
    val postTime: String = "",
    var appLink: String = "",
    var appDescription: String = "",
    val appId: String = userId + postTime,
    var tester: Int = 0,
    var activation: Int = 0,
    var appIcon: Uri? = null
)

data class AppIcon(
    val userId: String,
    val postTime: String,
    val imgUrl: Uri,
    val appId: String = userId + postTime
)