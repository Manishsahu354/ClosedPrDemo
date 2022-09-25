package com.assignment.naviassingment.repository

import androidx.lifecycle.LiveData
import com.assignment.naviassingment.data.model.PullRequestModel
import com.assignment.naviassingment.data.network.PullRequestApi
import retrofit2.Response
import javax.inject.Inject

class PullRequestRepository @Inject constructor(
    private val pullRequestApi: PullRequestApi
){
    suspend fun getClosedPullRequest(userName:String,projectName:String): Response<MutableList<PullRequestModel>> {
       return pullRequestApi.getPullRequests(
            userName = userName,
            projectName = projectName,
            state = "closed"
        )
    }
}