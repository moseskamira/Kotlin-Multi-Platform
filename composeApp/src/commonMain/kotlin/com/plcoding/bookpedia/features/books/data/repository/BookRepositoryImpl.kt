package com.plcoding.bookpedia.features.books.data.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.features.books.data.mappers.toBook
import com.plcoding.bookpedia.features.books.data.mappers.toBookEntity
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.features.books.data.database.FavoriteBookDao
import com.plcoding.bookpedia.features.books.data.network.AppNetworkResponse
import com.plcoding.bookpedia.features.books.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.features.books.domain.models.Book
import com.plcoding.bookpedia.features.books.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(
        query: String
    ): AppNetworkResponse<List<Book>> {
        return try {
            val response = remoteBookDataSource.searchBooks(query)
            AppNetworkResponse.Success(
                response.results.map { it.toBook() }
            )
        } catch (e: Exception) {
            AppNetworkResponse.Error(
                e.message ?: "Something went wrong"
            )
        }
    }

    override suspend fun getBookDescription(bookId: String): String {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)
        return if (localResult != null) {
            localResult.description!!
        } else {
            val remoteResult = remoteBookDataSource.getBookDetails(bookId)
            remoteResult.description.orEmpty()
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }
    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }



    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}