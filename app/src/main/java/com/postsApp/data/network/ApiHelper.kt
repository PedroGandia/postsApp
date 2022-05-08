package com.postsApp.data.network

import com.postsApp.data.network.model.*
import io.reactivex.Observable

interface ApiHelper {
    fun getUserList(): Observable<MutableList<User>?>
    fun getPostList(userId: Long): Observable<MutableList<Post>?>
}