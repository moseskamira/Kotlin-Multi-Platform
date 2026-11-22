package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.CreateBookRequest
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto

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