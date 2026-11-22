package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.CreateBookRequest
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val BASE_URL = "https://openlibrary.org"

class RemoteBookDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): SearchResponseDto {
        return httpClient.get("$BASE_URL/search.json") {
            parameter("q", query)
            parameter("limit", resultLimit)
            parameter("language", "eng")
            parameter(
                "fields",
                "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
            )
        }.body<SearchResponseDto>()
    }

    override suspend fun getBookDetails(
        bookWorkId: String
    ): BookWorkDto {
        return httpClient.get("$BASE_URL/works/$bookWorkId.json")
            .body<BookWorkDto>()
    }

    override suspend fun createBook(
        request: CreateBookRequest
    ): BookWorkDto {
        return httpClient.post("$BASE_URL/books") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}