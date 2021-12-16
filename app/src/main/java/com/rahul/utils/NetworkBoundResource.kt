package com.rahul.utils

import kotlinx.coroutines.flow.*

inline fun <localApi, apiType> networkBoundResource(

    crossinline query: () -> Flow<localApi>,
    crossinline fetch: suspend () -> apiType,
    crossinline saveFetchedData: suspend (apiType) -> Unit,
    crossinline shouldFetch: (localApi) -> Boolean = { true }


) = flow {

    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchedData(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(String(), it) }   //throwable changed to string()
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emit(flow)

}