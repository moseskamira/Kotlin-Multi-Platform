package com.plcoding.bookpedia.features.books.data.network

sealed interface AppNetworkResponse<out T> {

    data class Success<T>(
        val data: T
    ) : AppNetworkResponse<T>

    data class Error(
        val message: String
    ) : AppNetworkResponse<Nothing>
}