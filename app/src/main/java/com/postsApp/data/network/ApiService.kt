package com.postsApp.data.network

import com.postsApp.data.Config
import com.postsApp.data.network.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type:application/json", "Accept:application/json")
    @GET(Config.URL_USERS)
    fun getUserList(): Observable<MutableList<User>?>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @GET(Config.URL_POSTS)
    fun getPostList(@Query("userId") userId: Long): Observable<MutableList<Post>?>
}