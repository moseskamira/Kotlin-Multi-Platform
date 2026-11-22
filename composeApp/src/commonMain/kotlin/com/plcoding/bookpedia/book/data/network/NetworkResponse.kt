package com.plcoding.bookpedia.book.data.network

sealed interface NetworkResponse<out T> {

    data class Success<T>(
        val data: T
    ) : NetworkResponse<T>

    data class Error(
        val message: String
    ) : NetworkResponse<Nothing>
}