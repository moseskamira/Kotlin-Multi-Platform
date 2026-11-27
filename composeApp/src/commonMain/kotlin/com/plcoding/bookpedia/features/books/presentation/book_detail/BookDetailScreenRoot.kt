
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedBook by viewModel.selectedBook.collectAsStateWithLifecycle()
    BookDetailScreen(
        state = state,
        book = selectedBook,
        onBackClick = onBackClick,
        onFavoriteClick = viewModel::onFavoriteClick
    )
}
