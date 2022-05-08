package com.postsApp.ui.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.postsApp.data.DataManager
import com.postsApp.data.network.model.Post
import com.postsApp.data.network.model.User
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable

class PostsViewModel(application: Application, private val dataManager: DataManager) : AndroidViewModel(application) {

    fun getPostListFromDatabase(userId: Long): Observable<MutableList<Post>?> {
        return Observable.defer { Observable.just(dataManager.selectPostList(userId)) }
            .map { it as MutableList<Post>? }
            .subscribeOn(Schedulers.computation())
    }

    fun getPostListService(userId: Long): Observable<MutableList<Post>?> {
        return dataManager.getPostList(userId)
            .map {
                dataManager.deletePostList(userId)
                dataManager.insertPostList(it)
                it
            }
            .subscribeOn(Schedulers.io())
    }

    fun getUserFromDatabase(userId: Long): Observable<User?> {
        return Observable.defer { Observable.just(dataManager.selectUser(userId)) }
            .map {
                it
            }
            .subscribeOn(Schedulers.computation())
    }

}