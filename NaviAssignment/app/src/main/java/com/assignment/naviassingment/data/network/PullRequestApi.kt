package com.assignment.naviassingment.data.network

import androidx.lifecycle.LiveData
import com.assignment.naviassingment.data.model.PullRequestModel
import com.assignment.naviassingment.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PullRequestApi {

    @GET("${Constants.REPOS}{userName}/{projectName}/pulls")
    suspend fun getPullRequests(
        @Path("userName") userName:String,
        @Path("projectName") projectName:String,
        @Query("state") state:String
    ):Response<MutableList<PullRequestModel>>
}