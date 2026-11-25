package com.plcoding.bookpedia.features.books.presentation.book_detail
import com.plcoding.bookpedia.features.books.domain.models.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}