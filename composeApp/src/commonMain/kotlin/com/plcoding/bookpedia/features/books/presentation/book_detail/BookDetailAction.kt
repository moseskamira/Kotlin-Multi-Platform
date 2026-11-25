package com.plcoding.bookpedia.features.books.presentation.book_detail

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
}