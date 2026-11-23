package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateBookRequest(
    val title: String,
    val author: String
)
