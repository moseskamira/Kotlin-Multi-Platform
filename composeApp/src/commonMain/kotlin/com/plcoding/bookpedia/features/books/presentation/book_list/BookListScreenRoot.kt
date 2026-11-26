package com.plcoding.bookpedia.features.books.presentation.book_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.features.books.domain.models.Book
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val books by viewModel.books.collectAsStateWithLifecycle()
    val favoriteBooks by viewModel.favoriteBooks.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsStateWithLifecycle()
    BookListScreen(
        books = books,
        favoriteBooks = favoriteBooks,
        isLoading = isLoading,
        errorMessage = errorMessage,
        searchQuery = searchQuery,
        selectedTabIndex = selectedTabIndex,
        onBookClicked = {
            onBookClick(it)
        },
        onSearch = {
            viewModel.updateSearchQuery(it)
        },
        onTabChanged = {
            viewModel.updateTabIndex(it)
        }

    )
}