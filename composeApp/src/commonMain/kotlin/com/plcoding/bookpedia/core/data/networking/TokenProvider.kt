package com.plcoding.bookpedia.core.data.networking

interface TokenProvider {
    fun getAccessToken(): String?

    fun updateToken(token: String?)
}