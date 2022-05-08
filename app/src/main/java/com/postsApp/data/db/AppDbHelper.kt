package com.postsApp.data.db

import com.postsApp.data.db.room.AppDatabase
import com.postsApp.data.network.model.*
import javax.inject.Inject

class AppDbHelper @Inject
constructor(private val appDatabase: AppDatabase) : DbHelper {

    override fun insertUserList(userList: MutableList<User>): List<Long> {
        return appDatabase.userDao().insertUserList(userList)
    }

    override fun selectUserList(): List<User> {
        return appDatabase.userDao().selectUserList()
    }

    override fun deleteUserList() {
        appDatabase.userDao().deleteUserList()
    }

    override fun selectUser(id: Long): User {
        return appDatabase.userDao().selectUser(id)
    }

    override fun insertPostList(postList: MutableList<Post>): List<Long> {
        return appDatabase.postDao().insertPostList(postList)
    }

    override fun selectPostList(userId: Long): List<Post> {
        return appDatabase.postDao().selectPostList(userId)
    }

    override fun deletePostList(userId: Long) {
        appDatabase.postDao().deletePostList(userId)
    }
}