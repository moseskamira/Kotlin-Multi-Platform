package com.plcoding.bookpedia.features.books.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.features.books.data.network.AppNetworkResponse
import com.plcoding.bookpedia.features.books.domain.models.Book
import com.plcoding.bookpedia.features.books.domain.repositories.BookRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private var cachedBooks = emptyList<Book>()
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()
    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks = _favoriteBooks.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()
    private val _searchQuery = MutableStateFlow("Kotlin")
    val searchQuery = _searchQuery.asStateFlow()
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    init {
        observeFavoriteBooks()
        observeSearchQuery()
    }
    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun updateTabIndex(index: Int){
        _selectedTabIndex.value = index
    }

    private fun observeFavoriteBooks() {
        viewModelScope.launch {
            bookRepository.getFavoriteBooks()
                .catch { e ->
                    _errorMessage.value =
                        e.message ?: "Failed to load favorite books"
                }
                .collectLatest {
                    _favoriteBooks.value = it
                }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    when {
                        query.isBlank() -> {
                            _isLoading.value = false
                            _errorMessage.value = null
                            _books.value = cachedBooks
                        }
                        query.length >= 2 -> {
                            searchBooks(query)
                        }
                        else -> {
                            _isLoading.value = false
                            _errorMessage.value = null
                            _books.value = emptyList()
                        }
                    }
                }
        }
    }

    private suspend fun searchBooks(query: String) {
        _isLoading.value = true
        _errorMessage.value = null
        when (val response = bookRepository.searchBooks(query)) {
            is AppNetworkResponse.Success -> {
                cachedBooks = response.data
                _books.value = response.data
            }

            is AppNetworkResponse.Error -> {
                _errorMessage.value = response.message
                _books.value = cachedBooks
            }
        }
        _isLoading.value = false
    }
}
