package com.androidimportantconcepts.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.androidimportantconcepts.BaseApplication
import com.androidimportantconcepts.DashboardActivity
import com.androidimportantconcepts.Model.Post
import com.androidimportantconcepts.repository.PostRepository
import com.androidimportantconcepts.utils.SealedBaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 15-08-2022
 * Package : com.androidimportantconcepts.viewmodel
 */

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    val responseLD = MutableLiveData<List<Post>>()

    private lateinit var postJob: Job

    fun getPost() {
        postJob = viewModelScope.launch {
            postRepository.getPosts()
                .catch { e ->
                    Log.e("TAG", "Error ${e.message}")
                }
                .collect {
                    when (it) {
                        is SealedBaseResponse.Loading -> {
                            Log.d("TAG", "Loading....")
                        }
                        is SealedBaseResponse.Success -> {
                             it.data?.let { listOfPosts ->
                                 responseLD.value = listOfPosts
                             }
                        }
                        is SealedBaseResponse.Failure -> {
                            Log.e("TAG", "failure: ${it.msg}")
                        }
                    }
                }
        }

    }


    override fun onCleared() {
        super.onCleared()
        Log.e("TAG", "onCleared: ")
        if (::postJob.isInitialized) postJob.cancel()
    }
}