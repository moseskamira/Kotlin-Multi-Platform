package com.plcoding.bookpedia.features.books.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("docs") val booksDto: List<SearchedBookDto>
)
