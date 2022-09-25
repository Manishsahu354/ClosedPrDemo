package com.assignment.naviassingment

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.naviassingment.data.model.PullRequestModel
import com.assignment.naviassingment.databinding.ActivityMainBinding
import com.assignment.naviassingment.ui.PullRequestAdapter
import com.assignment.naviassingment.util.Constants
import com.assignment.naviassingment.util.NetworkResult
import com.assignment.naviassingment.viewmodel.PullRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pullRequestAdapter: PullRequestAdapter
    private var closedPrList:MutableList<PullRequestModel> = mutableListOf()
    private lateinit var pullRequestViewModel: PullRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerview()
        setupViewModel()

    }

    private fun setupViewModel() {
        pullRequestViewModel = ViewModelProvider(this)[PullRequestViewModel::class.java]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pullRequestViewModel.getCosedPullRequest("Manishsahu354","BookMyShowClone")
        }
        pullRequestViewModel.closedPullRequestData.observe(this){response->
            when(response){
                is NetworkResult.Loading -> {
                   binding.progressBar.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let {resultList->
                        if (resultList.isEmpty()){
                            binding.tvErrorMsg.text = "No Data Found"
                        }else{
                            pullRequestAdapter.setData(resultList)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (response.message.equals(Constants.NO_INTERNET_CONNECTION)){
                        binding.tvErrorMsg.text = "No Internet Connection"
                    }else{
                        binding.tvErrorMsg.text = response.message
                    }
                }
                else -> {}
            }
        }
    }

    private fun setupRecyclerview() {
        pullRequestAdapter = PullRequestAdapter(closedPrList,this)
        binding.closedPrRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pullRequestAdapter
        }
    }
}