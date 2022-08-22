package com.androidimportantconcepts.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


/**
 * Project Name : ContentProviderDemo
 * Created By : Himanshu Sharma on 21-08-2022
 * Package : com.androidimportantconcepts.utils
 */

object BaseResponse{
    fun<T> getBaseResponse(call : suspend ()->Response<T>): Flow<SealedBaseResponse<T?>> = flow {
        emit(SealedBaseResponse.Loading)
        try {
            val response = call()
            response.let {
                if (it.isSuccessful){
                    emit(SealedBaseResponse.Success(it.body()))
                }else{
                    response.errorBody()?.let { error ->
                        error.close()
                        emit(SealedBaseResponse.Failure(error.toString()))
                    }
                }
            }
        }catch (e : Exception){
            e?.printStackTrace()
            emit(SealedBaseResponse.Failure(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
