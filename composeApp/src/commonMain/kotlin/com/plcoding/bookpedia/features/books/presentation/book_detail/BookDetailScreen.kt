
@file:OptIn(ExperimentalLayoutApi::class)

package com.plcoding.bookpedia.features.books.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.description_unavailable
import cmp_bookpedia.composeapp.generated.resources.languages
import cmp_bookpedia.composeapp.generated.resources.pages
import cmp_bookpedia.composeapp.generated.resources.rating
import cmp_bookpedia.composeapp.generated.resources.synopsis
import com.plcoding.bookpedia.core.presentation.SandYellow
import com.plcoding.bookpedia.features.books.domain.models.Book
import com.plcoding.bookpedia.features.books.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.features.books.presentation.book_detail.components.BookChip
import com.plcoding.bookpedia.features.books.presentation.book_detail.components.ChipSize
import com.plcoding.bookpedia.features.books.presentation.book_detail.components.TitledContent
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun BookDetailScreen(
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    book: Book?,
    isLoading: Boolean,
    isFavorite: Boolean
) {
    BlurredImageBackground(
        imageUrl = book?.imageUrl,
        isFavorite = isFavorite,
        onFavoriteClick = onFavoriteClick,
        onBackClick = onBackClick,
        modifier = Modifier.fillMaxSize()
    ) {
        book?.let {
            BookDetailContent(
                book = it,
                isLoading= isLoading
            )
        }
    }
}

@Composable
private fun BookDetailContent(
    book: Book,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .widthIn(max = 700.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookHeader(book = book)
        BookStatistics(book = book)
        BookLanguages(languages = book.languages)
        SynopsisSection(
            description = book.description,
            isLoading = isLoading
        )
    }
}

@Composable
private fun BookHeader(book: Book) {
    Text(
        text = book.title,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )

    Text(
        text = book.authors.joinToString(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BookStatistics(book: Book) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        book.averageRating?.let { rating ->
            TitledContent(
                title = stringResource(Res.string.rating)
            ) {
                BookChip {
                    Text(text = "${round(rating * 10) / 10.0}")
                    val fullStars = rating.toInt()
                    val hasHalfStar = (rating - fullStars) >= 0.5
                    repeat(fullStars) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }
                    if (hasHalfStar) {
                        Icon(
                            imageVector = Icons.Default.StarHalf,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }
                }
            }
        }

        book.numPages?.let { pageCount ->
            TitledContent(
                title = stringResource(Res.string.pages)
            ) {
                BookChip {
                    Text(text = pageCount.toString())
                }
            }
        }
    }
}

@Composable
private fun BookLanguages(languages: List<String>) {
    if (languages.isEmpty()) return
    TitledContent(
        title = stringResource(Res.string.languages),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            languages.forEach { language ->
                BookChip(
                    size = ChipSize.SMALL,
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text(
                        text = language.uppercase(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun SynopsisSection(
    description: String?,
    isLoading: Boolean
) {
    Text(
        text = stringResource(Res.string.synopsis),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
    )

    if (isLoading) {
        CircularProgressIndicator()
        return
    }
    val hasDescription = !description.isNullOrBlank()
    Text(
        text = description.takeUnless { it.isNullOrBlank() }
            ?: stringResource(Res.string.description_unavailable),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify,
        color = if (hasDescription) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        },
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
