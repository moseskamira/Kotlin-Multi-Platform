package com.plcoding.bookpedia.core.data.networking

class TokenProviderImpl : TokenProvider {

    private var _accessToken: String? = null

    override fun getAccessToken(): String? {
        return _accessToken
    }

    override fun updateToken(token: String?) {
        _accessToken = token
    }
}