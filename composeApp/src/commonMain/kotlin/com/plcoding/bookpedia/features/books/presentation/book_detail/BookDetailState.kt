package com.plcoding.bookpedia.features.books.presentation.book_detail

import com.plcoding.bookpedia.features.books.domain.models.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
