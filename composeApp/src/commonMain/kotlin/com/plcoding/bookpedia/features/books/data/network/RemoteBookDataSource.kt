package com.plcoding.bookpedia.features.books.data.network

import com.plcoding.bookpedia.features.books.data.dto.BookWorkDto
import com.plcoding.bookpedia.features.books.data.dto.CreateBookRequest
import com.plcoding.bookpedia.features.books.data.dto.SearchResponseDto

interface RemoteBookDataSource {

    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): SearchResponseDto

    suspend fun getBookDetails(
        bookWorkId: String
    ): BookWorkDto

    suspend fun createBook(
        request: CreateBookRequest
    ): BookWorkDto


}