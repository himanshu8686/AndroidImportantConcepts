package com.androidimportantconcepts.Network

import com.androidimportantconcepts.Model.Post
import retrofit2.Response
import retrofit2.http.GET


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 15-08-2022
 * Package : com.androidimportantconcepts.Network
 */
interface ApiInterface {

    @GET("posts")
    suspend fun getPost(): Response<List<Post>>

}