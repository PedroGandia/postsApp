package com.postsApp.data.db.room.dao

import androidx.room.*
import com.postsApp.data.network.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPostList(postList: MutableList<Post>): List<Long>

    @Query("SELECT * FROM posts WHERE userId=:userId")
    fun selectPostList(userId: Long): List<Post>

    @Query("DELETE FROM posts WHERE userId=:userId")
    fun deletePostList(userId: Long)
}