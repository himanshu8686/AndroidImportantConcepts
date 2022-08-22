package com.androidimportantconcepts.utils


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 21-08-2022
 * Package : com.androidimportantconcepts.utils
 */
sealed class SealedBaseResponse<out T> {

    data class Success<out R>(val data:R) : SealedBaseResponse<R>()
    data class Failure(val msg : String) : SealedBaseResponse<Nothing>()
    object Loading : SealedBaseResponse<Nothing>()

}