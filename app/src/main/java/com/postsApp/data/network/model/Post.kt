package com.postsApp.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.postsApp.data.db.room.Converters

@Entity(tableName = "posts")
@TypeConverters(Converters::class)
data class Post(
    @PrimaryKey(autoGenerate = false)
    @Expose
    var id: Long? = null,

    @SerializedName("userId")
    @Expose
    @ColumnInfo(name = "userId")
    var userId: Long? = null,

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String? = null,

    @SerializedName("body")
    @Expose
    @ColumnInfo(name = "body")
    var body: String? = null
)
