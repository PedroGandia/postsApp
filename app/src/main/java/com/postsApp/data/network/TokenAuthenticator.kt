package com.postsApp.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(private val apiServiceAuth: ApiServiceAuth) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return null
    }
}