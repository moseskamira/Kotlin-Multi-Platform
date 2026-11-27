
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.features.books.presentation.book_detail.BookDetailScreen
import com.plcoding.bookpedia.features.books.presentation.book_detail.BookDetailViewModel

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit
) {
    val selectedBook by viewModel.selectedBook.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    BookDetailScreen(
        book = selectedBook,
        onBackClick = onBackClick,
        onFavoriteClick = { selectedBook?.let { viewModel.onFavoriteClick(it) } },
        isLoading = isLoading,
        isFavorite = isFavorite
    )
}
