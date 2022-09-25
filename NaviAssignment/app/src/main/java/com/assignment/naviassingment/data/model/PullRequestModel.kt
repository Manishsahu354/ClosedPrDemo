package com.assignment.naviassingment.data.model

import com.google.gson.annotations.SerializedName

data class PullRequestModel(
    val title:String?,
    @SerializedName("created_at")
    val createdDate:String?,
    @SerializedName("closed_at")
    val closedDate:String?,
    val user:User
)
