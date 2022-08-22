package com.androidimportantconcepts.di

import com.androidimportantconcepts.Network.ApiInterface
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 14-08-2022
 * Package : com.androidimportantconcepts.di
 */

@Module
@InstallIn(SingletonComponent::class)
class AppLevelDI {

    @Provides
    fun providesBaseUrl(): String = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun providesRetrofitBuilder(baseUrl: String): ApiInterface {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
}