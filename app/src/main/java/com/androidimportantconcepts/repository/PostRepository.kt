package com.androidimportantconcepts.repository

import com.androidimportantconcepts.Network.ApiInterfaceImpl
import com.androidimportantconcepts.utils.BaseResponse
import javax.inject.Inject

/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 15-08-2022
 * Package : com.androidimportantconcepts.repository
 */
class PostRepository @Inject constructor(private val apiInterfaceImpl: ApiInterfaceImpl) {

    fun getPosts() = BaseResponse.getBaseResponse {
        apiInterfaceImpl.getPost()
    }
}