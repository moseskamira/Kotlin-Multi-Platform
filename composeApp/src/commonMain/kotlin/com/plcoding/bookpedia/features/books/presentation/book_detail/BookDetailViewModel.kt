
package com.plcoding.bookpedia.features.books.presentation.book_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.features.books.domain.models.Book
import com.plcoding.bookpedia.features.books.domain.repositories.BookRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private var bookId: String? = null
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()
    private var favoriteStatusJob: Job? = null
    private var descriptionJob: Job? = null

    fun onSelectBook(book: Book?) {
        bookId = book?.id
        _selectedBook.value = book
        _state.update {
            it.copy(
                book = book,
                isLoading = true,
                isFavorite = false
            )
        }
        favoriteStatusJob?.cancel()
        descriptionJob?.cancel()
        fetchBookDescription()
        observeFavoriteStatus()
    }

    fun onFavoriteClick(){
        viewModelScope.launch {
            val id = bookId ?: return@launch
            if (state.value.isFavorite) {
                bookRepository.deleteFromFavorites(id)
            } else {
                state.value.book?.let { book ->
                    bookRepository.markAsFavorite(book)
                }
            }
        }

    }

    private fun observeFavoriteStatus() {
        val id = bookId ?: return
        favoriteStatusJob = bookRepository
            .isBookFavorite(id)
            .onEach { isFavorite ->
                if (bookId == id) {
                    _state.update {
                        it.copy(isFavorite = isFavorite)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        val id = bookId ?: return
        descriptionJob = viewModelScope.launch {
            try {
                val description = bookRepository.getBookDescription(id)

                if (bookId == id) {
                    _state.update {
                        it.copy(
                            book = it.book?.copy(description = description),
                            isLoading = false
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (bookId == id) {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}
