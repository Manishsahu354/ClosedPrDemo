package com.assignment.naviassingment.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val userImage:String?
)