package com.androidimportantconcepts.Network

import com.androidimportantconcepts.Model.Post
import retrofit2.Response
import javax.inject.Inject


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 15-08-2022
 * Package : com.androidimportantconcepts.Network
 */
class ApiInterfaceImpl @Inject constructor(private val apiInterface : ApiInterface) {

    suspend fun getPost() : Response<List<Post>> {
        return apiInterface.getPost()
    }
}