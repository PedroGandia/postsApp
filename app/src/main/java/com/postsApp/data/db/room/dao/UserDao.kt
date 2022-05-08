package com.postsApp.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postsApp.data.network.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(userList: MutableList<User>): List<Long>

    @Query("SELECT * FROM users")
    fun selectUserList(): List<User>

    @Query("DELETE FROM users")
    fun deleteUserList()

    @Query("SELECT * FROM users WHERE id=:id")
    fun selectUser(id: Long): User
}