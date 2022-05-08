package com.postsApp.data.network

import com.postsApp.data.network.model.*
import io.reactivex.Observable
import javax.inject.Inject

class AppApiHelper @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getUserList(): Observable<MutableList<User>?> {
        return apiService.getUserList()
    }

    override fun getPostList(userId: Long): Observable<MutableList<Post>?> {
        return apiService.getPostList(userId)
    }
}