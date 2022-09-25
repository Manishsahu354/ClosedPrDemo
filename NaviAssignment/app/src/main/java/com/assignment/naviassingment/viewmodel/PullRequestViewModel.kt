package com.assignment.naviassingment.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.assignment.naviassingment.data.model.PullRequestModel
import com.assignment.naviassingment.repository.PullRequestRepository
import com.assignment.naviassingment.util.Constants
import com.assignment.naviassingment.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val repository: PullRequestRepository,
    application: Application
): AndroidViewModel(application) {

    private val _closedPullRequestData:MutableLiveData<NetworkResult<MutableList<PullRequestModel>>?> = MutableLiveData()
    val closedPullRequestData: LiveData<NetworkResult<MutableList<PullRequestModel>>?> = _closedPullRequestData

    @RequiresApi(Build.VERSION_CODES.M)
    fun getCosedPullRequest(userName:String, projectName:String){
        viewModelScope.launch {
            _closedPullRequestData.postValue(NetworkResult.Loading())

            if (hasInternetConnection()){
                val response =repository.getClosedPullRequest(userName,projectName)
                when (response.isSuccessful) {
                    true -> {
                        _closedPullRequestData.postValue(NetworkResult.Success(response.body()))
                    }
                    else -> {
                        _closedPullRequestData.postValue(NetworkResult.Error(response.message()))
                    }
                }
            }else{
                _closedPullRequestData.postValue(NetworkResult.Error(Constants.NO_INTERNET_CONNECTION))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false;
        }
    }
}