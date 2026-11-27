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
    private val _selectedBook = MutableStateFlow<Book?>(null)
    private val _bookToFavorite = MutableStateFlow<Book?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _isFavorite = MutableStateFlow(false)
    val selectedBook = _selectedBook.asStateFlow()
    val isLoading = _isLoading.asStateFlow()
    val isFavorite = _isFavorite.asStateFlow()

    private var favoriteStatusJob: Job? = null
    private var descriptionJob: Job? = null

    fun onSelectBook(book: Book?) {
        bookId = book?.id
        _selectedBook.value = book
        _isLoading.value = true;
        favoriteStatusJob?.cancel()
        descriptionJob?.cancel()
        fetchBookDescription()
        observeFavoriteStatus()
    }

    fun onFavoriteClick(book: Book) {
        viewModelScope.launch {
            _bookToFavorite.value = book
            val id = bookId ?: return@launch
            if (isFavorite.value) {
                bookRepository.deleteFromFavorites(id)
            } else {
                _bookToFavorite.value?.let { book ->
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
                    _isFavorite.value = isFavorite
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
                    _isLoading.value = false
                    _bookToFavorite.update { book ->
                        book?.copy(
                            description = description
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (bookId == id) {
                    _isLoading.value = false;
                }
            }
        }
    }
}
