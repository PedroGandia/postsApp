package com.postsApp.data

import com.postsApp.data.db.AppDbHelper
import com.postsApp.data.network.AppApiHelper
import com.postsApp.data.network.model.*
import io.reactivex.Observable
import javax.inject.Inject

class AppDataManager @Inject constructor(
    private val appApiHelper: AppApiHelper,
    private val appDbHelper: AppDbHelper
) : DataManager {

    override fun getUserList(): Observable<MutableList<User>?> {
        return appApiHelper.getUserList()
    }

    override fun getPostList(userId: Long): Observable<MutableList<Post>?> {
        return appApiHelper.getPostList(userId)
    }

    override fun insertUserList(userList: MutableList<User>): List<Long> {
        return appDbHelper.insertUserList(userList)
    }

    override fun selectUserList(): List<User> {
        return appDbHelper.selectUserList()
    }

    override fun deleteUserList() {
        appDbHelper.deleteUserList()
    }

    override fun selectUser(id: Long): User {
        return appDbHelper.selectUser(id)
    }

    override fun insertPostList(postList: MutableList<Post>): List<Long> {
        return appDbHelper.insertPostList(postList)
    }

    override fun selectPostList(userId: Long): List<Post> {
        return appDbHelper.selectPostList(userId)
    }

    override fun deletePostList(userId: Long) {
        appDbHelper.deletePostList(userId)
    }
}