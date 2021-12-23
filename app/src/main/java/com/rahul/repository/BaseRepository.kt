//package com.rahul.repository
//
//import com.rahul.utils.Resource
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.HttpException
//import javax.inject.Inject
//
//abstract class BaseRepository @Inject constructor() {
//
//
//    suspend fun<T> safeApiCall(apiCall : suspend () ->T) : Resource<T>{
//
//        return withContext(Dispatchers.IO){
//            try {
//                Resource.Success(apiCall.invoke())
//            }catch (throwable : Throwable){
//                when(throwable){
//                    is HttpException ->{
//                        Resource.Error(false,throwable.code().toString(),throwable.response()!!.errorBody()!!)
//                    }
//                    else ->{
//                        Resource.Error(true)
//                    }
//                }
//            }
//        }
//    }
//}