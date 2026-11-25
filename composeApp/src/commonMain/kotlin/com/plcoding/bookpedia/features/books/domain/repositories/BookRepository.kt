package com.plcoding.bookpedia.features.books.domain.repositories

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.features.books.data.network.AppNetworkResponse
import com.plcoding.bookpedia.features.books.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): AppNetworkResponse<List<Book>>
    suspend fun getBookDescription(bookId: String): String
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>

}