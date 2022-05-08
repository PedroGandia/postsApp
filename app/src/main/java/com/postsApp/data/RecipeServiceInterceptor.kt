package com.postsApp.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RecipeServiceInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}