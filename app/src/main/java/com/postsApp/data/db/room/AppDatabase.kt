package com.postsApp.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.postsApp.BuildConfig.VERSION_CODE
import com.postsApp.data.db.room.dao.*
import com.postsApp.data.network.model.*

@Database(
    version = VERSION_CODE,
    entities = [User::class, Post::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}