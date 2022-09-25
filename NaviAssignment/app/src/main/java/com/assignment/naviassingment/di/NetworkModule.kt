package com.assignment.naviassingment.di

import com.assignment.naviassingment.data.network.PullRequestApi
import com.assignment.naviassingment.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePullRequestApi(retrofit:Retrofit):PullRequestApi{
        return retrofit.create(PullRequestApi::class.java)
    }
}