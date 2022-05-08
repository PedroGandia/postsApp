package com.postsApp.data.db

import com.postsApp.data.network.model.*

interface DbHelper {

    fun insertUserList(userList: MutableList<User>): List<Long>

    fun selectUserList(): List<User>

    fun deleteUserList()

    fun selectUser(id: Long): User

    fun insertPostList(postList: MutableList<Post>): List<Long>

    fun selectPostList(userId: Long): List<Post>

    fun deletePostList(userId: Long)
}